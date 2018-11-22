/*
 * Copyright  2018  wonium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wonium.extension.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.os.Environment;

import com.wonium.extension.config.TypeCommon;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
/**
 * @ClassName: BitmapUtil.java
 * @Description: Bitmap 工具类
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/12 21:01
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/12 21:01
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public enum BitmapUtil {
    /**
     * 实例对象
     */
    INSTANCE;

    public static final boolean IS_SAVE_BITMAP = false;

    /**
     * 创建一个指定宽，高，颜色的bitmap
     * @param width bitmap的宽，单位是像素
     * @param height bitmap的高，单位是像素
     * @param color bitmap 的颜色 例如getResources().getColor(R.color.colorAccent),Color.RED，Color.parseColor("#FFOOFO")等
     * @return
     */
    public  Bitmap createBitmap(int width, int height, int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(rect, paint);
        return bitmap;
    }


    /**
     * res 文件夹下的图片转Bitmap
     *
     * @param context 上下文
     * @param resId   资源图片ID
     * @return 资源图片转成的Bitmap
     * @throws NullPointerException
     */
    public  Bitmap imgToBitmap(Context context, int resId) throws NullPointerException {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    /**
     * 获取Bitmap 长度
     *
     * @param bitmap 被计算的bitmap
     * @return bitmap 的长度
     */
    public  int getBitmapSize(Bitmap bitmap) {
        int length = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        length += baos.toByteArray().length;
        return length;
    }

    /**
     * bitmap 转字节数据
     *
     * @param bitmap 被转换bitmap
     * @return
     */

    public  byte[] bitmapToByte(Bitmap bitmap) {
        if (bitmap == null) {
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * Bitmap 转成字节数组
     *
     * @param bitmap 源Bitmap
     * @return byte[] bitmap 转换后的字节数组
     */

    public  byte[] convertBitmapToByte(Bitmap bitmap) {
        byte[] datas = bitmapToByte(bitmap);
        int[] intDatas = ByteUtil.INSTANCE.bytesToInts(datas);
        byte[] result = new byte[0];
        for (int i = 0; i < intDatas.length; i++) {
            intDatas[i] = intDatas[i] & 0x00FFFFFF;
            result = ByteUtil.INSTANCE.appendData(result, ByteUtil.INSTANCE.intToBytes(intDatas[i]));
        }
        return result;
    }


    /**
     * 保存图片
     *
     * @param bitmap 图片
     * @param name   索引 文件名
     * @return 文件path
     */

    public  String saveBitmap(Bitmap bitmap, String name) {
        File file = new File(Environment.getExternalStorageDirectory() + TypeCommon.ZH_PROGRAM_COVER);
        if (!file.exists())
            file.mkdirs();
        File imgFile = new File(file.getAbsolutePath() + "/" + name + TypeCommon.ZH_FILE_BMP_UFFIX);
        if (imgFile.exists())
            imgFile.delete();
        try {
            FileOutputStream outputStream = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgFile.getPath();
    }


    /**
     * 通过Path 获得Bitmap
     *
     * @param urlPath 文件路径
     * @return bitmap
     */
    public  Bitmap getBitmapByPath(String urlPath) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(urlPath);
            try {
                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 图片转换 Bitmap,并设置新的宽，高
     *
     * @param path   图片路径
     * @param width  新的宽
     * @param height 新的高
     * @return Bitmap
     */
    public  Bitmap imgToBitmap(String path, int width, int height) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        bitmap = BitmapUtil.INSTANCE.getZoomImage(bitmap, width, height);
        return bitmap;
    }

    /**
     * Bitmap 翻转
     *
     * @param srcBitmap 原Bitmapf
     * @return new bitmap
     */
    public  Bitmap flippingBitmap(Bitmap srcBitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(1, -1);
        matrix.postRotate(0);
        return Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
    }


    /**
     * Bitmap 旋转，
     *
     * @param bitmap 被旋转得Bitmap
     * @param angle
     * @param sx
     * @param sy
     * @return
     */
    public  Bitmap rotateBitmap(Bitmap bitmap, float angle, float sx, float sy) {
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    /**
     * 将Bitmap存为 .bmp格式图片
     *
     * @param bitmap 原图片
     */
    public void saveBmp(Bitmap bitmap, String path) {
        if (bitmap == null) {
            return;
        }
        byte bmpData[];
        int nBmpWidth = bitmap.getWidth();
        int nBmpHeight = bitmap.getHeight();
        // 图像数据大小
        int bufferSize = nBmpHeight * nBmpWidth * 4;
        try {
            File file = new File(path);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fileos = new FileOutputStream(path);
            // bmp文件头
            int bfType = 0x4d42;
            long bfSize = 14 + 40 + bufferSize;
            int bfReserved1 = 0;
            int bfReserved2 = 0;
            long bfOffBits = 14 + 40;
            // 保存bmp文件头
            writeWord(fileos, bfType);
            writeDword(fileos, bfSize);
            writeWord(fileos, bfReserved1);
            writeWord(fileos, bfReserved2);
            writeDword(fileos, bfOffBits);
            // bmp信息头
            long biSize = 40L;
            int biPlanes = 1;
            int biBitCount = 32;
            long biCompression = 0L;
            long biSizeImage = 0L;
            long biXpelsPerMeter = 0L;
            long biYPelsPerMeter = 0L;
            long biClrUsed = 0L;
            long biClrImportant = 0L;
            // 保存bmp信息头
            writeDword(fileos, biSize);
            writeLong(fileos, (long) nBmpWidth);
            writeLong(fileos, (long) nBmpHeight);
            writeWord(fileos, biPlanes);
            writeWord(fileos, biBitCount);
            writeDword(fileos, biCompression);
            writeDword(fileos, biSizeImage);
            writeLong(fileos, biXpelsPerMeter);
            writeLong(fileos, biYPelsPerMeter);
            writeDword(fileos, biClrUsed);
            writeDword(fileos, biClrImportant);
            // 像素扫描
            bmpData = new byte[bufferSize];
            int wWidth = (nBmpWidth * 4);
            for (int nCol = 0, nRealCol = nBmpHeight - 1; nCol < nBmpHeight; ++nCol, --nRealCol) {
                for (int wRow = 0, wByteIdex = 0; wRow < nBmpWidth; wRow++, wByteIdex += 4) {
                    int clr = bitmap.getPixel(wRow, nCol);
                    bmpData[nRealCol * wWidth + wByteIdex] = (byte) Color.blue(clr);
                    bmpData[nRealCol * wWidth + wByteIdex + 1] = (byte) Color.green(clr);
                    bmpData[nRealCol * wWidth + wByteIdex + 2] = (byte) Color.red(clr);
                    bmpData[nRealCol * wWidth + wByteIdex + 3] = (byte) Color.alpha(0x00);
                }
            }
            fileos.write(bmpData);
            fileos.flush();
            fileos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取BMP 文件的RGB 数据
     *
     * @param srcBitmap 原Bitmap
     * @return bitmap的RGB数据
     */
    public  byte[] getBmpRGBData(Bitmap srcBitmap) {
        int nBmpWidth = srcBitmap.getWidth();
        int nBmpHeight = srcBitmap.getHeight();
        int bufferSize = nBmpHeight * nBmpWidth * 4;
        byte[] bmpData = new byte[bufferSize];
        int wWidth = (nBmpWidth * 4);
        int sumIndex;
        int clr;
        for (int nCol = 0, nRealCol = nBmpHeight - 1; nCol < nBmpHeight; ++nCol, --nRealCol) {
            for (int wRow = 0, wByteIndex = 0; wRow < nBmpWidth; wRow++, wByteIndex += 4) {
                clr = srcBitmap.getPixel(wRow, nCol);
                sumIndex = nRealCol * wWidth + wByteIndex;
                bmpData[sumIndex] = (byte) Color.blue(clr);
                bmpData[sumIndex + 1] = (byte) Color.green(clr);
                bmpData[sumIndex + 2] = (byte) Color.red(clr);
                bmpData[sumIndex + 3] = (byte) Color.alpha(0x00);//透明度必须为0
            }
        }
        return bmpData;
    }

    public  byte[] getPixelsBGRA(Bitmap image) {
        int bytes = image.getByteCount();

        ByteBuffer buffer = ByteBuffer.allocate(bytes); // Create a new buffer
        image.copyPixelsToBuffer(buffer); // Move the byte data to the buffer
        byte[] temp = buffer.array(); // Get the underlying array containing the data.
        byte[] pixels = new byte[temp.length]; // Allocate for BGRA

        for (int i = 0; i < (temp.length / 4); i++) {
            pixels[i * 4] = temp[i * 4 + 2];     // B
            pixels[i * 4 + 1] = temp[i * 4 + 1]; // G
            pixels[i * 4 + 2] = temp[i * 4];     // R
            pixels[i * 4 + 3] = 0x00; // A
            //          pixels[i * 4 + 3] = temp[i*4+3]; // A
        }
        return pixels;
    }

    /**
     * 获取BMP 文件的RGB 数据
     *
     * @param srcBitmap 原Bitmap
     * @return bitmap的RGB数据
     */
    public  byte[] getBmpRGBData24(Bitmap srcBitmap) {
        int bmpWidth = srcBitmap.getWidth();
        int bmpHeight = srcBitmap.getHeight();
        int bufferSize = bmpHeight * bmpWidth * 3;
        byte[] bmpData = new byte[bufferSize];
        int wWidth = (bmpWidth * 3);
        for (int nCol = 0, nRealCol = bmpHeight - 1; nCol < bmpHeight; ++nCol, --nRealCol) {
            for (int wRow = 0, wByteIndex = 0; wRow < bmpWidth; wRow++, wByteIndex += 3) {
                int clr = srcBitmap.getPixel(wRow, nCol);
                bmpData[nRealCol * wWidth + wByteIndex] = (byte) Color.blue(clr);
                bmpData[nRealCol * wWidth + wByteIndex + 1] = (byte) Color.green(clr);
                bmpData[nRealCol * wWidth + wByteIndex + 2] = (byte) Color.red(clr);
            }
        }
        return bmpData;
    }


    private  void writeWord(FileOutputStream stream, int value) throws IOException {
        byte[] b = new byte[2];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        stream.write(b);
    }

    private  void writeDword(FileOutputStream stream, long value) throws IOException {
        byte[] b = new byte[4];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        b[2] = (byte) (value >> 16 & 0xff);
        b[3] = (byte) (value >> 24 & 0xff);
        stream.write(b);
    }

    private  void writeLong(FileOutputStream stream, long value) throws IOException {
        byte[] b = new byte[4];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        b[2] = (byte) (value >> 16 & 0xff);
        b[3] = (byte) (value >> 24 & 0xff);
        stream.write(b);
    }

    /**
     * 将彩色图转换为黑白图
     *
     * @param bmp 位图
     * @return 返回转换好的位图
     */
    public  Bitmap convertToBlackWhite(Bitmap bmp) {
        int width = bmp.getWidth(); // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高
        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap newBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);

        return ThumbnailUtils.extractThumbnail(newBmp, width, height);
    }


    /**
     * 图片的缩放方法
     *
     * @param orgBitmap ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return bitmap
     */
    public Bitmap getZoomImage(Bitmap orgBitmap, double newWidth, double newHeight) {
        if (null == orgBitmap) {
            return null;
        }
        if (orgBitmap.isRecycled()) {
            return null;
        }
        if (newWidth <= 0 || newHeight <= 0) {
            return null;
        }

        // 获取图片的宽和高
        float width = orgBitmap.getWidth();
        float height = orgBitmap.getHeight();
        // 创建操作图片的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(orgBitmap, 0, 0, (int) width, (int) height, matrix, true);
    }


    /**
     * 保存图片交由异步线程处理
     *
     * @param bitmap 图片
     * @param path   图片路径
     */
    private void asynSaveBitmap(Bitmap bitmap, String path) {

        if (IS_SAVE_BITMAP) {
            ThreadPoolUtil.INSTANCE.execute(() -> BitmapUtil.INSTANCE.saveBmp(bitmap, path));
        }
    }
}
