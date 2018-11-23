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

import java.nio.ByteBuffer;

/**
 * @ClassName: ByteUtil.java
 * @Description: 字节处理工具类
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/12 21:02
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/12 21:02
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public enum  ByteUtil {

    /**
     * 实例对象
     */
    INSTANCE;
    /**
     * 获取index位置的bit值
     * @param value 字节数据
     * @param index 字节数据的bit位索引  index的范围为0-7
     * @return bit位的值 结果范围为0，1  例如 240 获取bit7位置的bit值  返回1
     */

    public byte getBitValueByIndex(byte value, int index) {
        return  (byte) (0x1&value >> index);
    }


    public void putShort(byte[] b, short s, int index) {
        b[index + 1] = (byte) (s >> 8);
        b[index] = (byte) (s);
    }

    public short getShort(byte[] b, int index) {
        //b[0]在高位,b[1]在低位,即BIG_ENDIAN
        return (short) ((b[index] << 8) | b[index + 1] & 0xff);
    }


    public void putInt(byte[] bb, int x, int index) {
        bb[index + 3] = (byte) (x >> 24);
        bb[index + 2] = (byte) (x >> 16);
        bb[index + 1] = (byte) (x >> 8);
        bb[index] = (byte) (x);
    }


    public int getInt(byte[] bb, int index) {
        return ((bb[index + 3] & 0xff) << 24) | ((bb[index + 2] & 0xff) << 16) | ((bb[index + 1] & 0xff) << 8) | ((bb[index] & 0xff));
    }

    public int getIntByBigendian(byte[] bb, int index) {
        return (((bb[index] & 0xff) << 24) | ((bb[index + 1] & 0xff) << 16) | ((bb[index + 2] & 0xff) << 8) | ((bb[index + 3] & 0xff)));
    }

    public void putLong(byte[] bb, long x, int index) {
        bb[index + 7] = (byte) (x >> 56);
        bb[index + 6] = (byte) (x >> 48);
        bb[index + 5] = (byte) (x >> 40);
        bb[index + 4] = (byte) (x >> 32);
        bb[index + 3] = (byte) (x >> 24);
        bb[index + 2] = (byte) (x >> 16);
        bb[index + 1] = (byte) (x >> 8);
        bb[index] = (byte) (x);
    }

    public long getLong(byte[] bb, int index) {
        return ((((long) bb[index + 7] & 0xff) << 56) | (((long) bb[index + 6] & 0xff) << 48) | (((long) bb[index + 5] & 0xff) << 40) | (((long) bb[index + 4] & 0xff) << 32) | (((long) bb[index + 3] & 0xff) << 24) | (((long) bb[index + 2] & 0xff) << 16) | (((long) bb[index + 1] & 0xff) << 8) | (((long) bb[index] & 0xff)));
    }

    public long getLongByBigendian(byte[] bb, int index) {
        return ((((long) bb[index] & 0xff) << 56) | (((long) bb[index + 1] & 0xff) << 48) | (((long) bb[index + 2] & 0xff) << 40) | (((long) bb[index + 3] & 0xff) << 32) | (((long) bb[index + 4] & 0xff) << 24) | (((long) bb[index + 5] & 0xff) << 16) | (((long) bb[index + 6] & 0xff) << 8) | (((long) bb[index + 7] & 0xff)));
    }

    public void putChar(byte[] bb, char ch, int index) {
        int temp = (int) ch;
        // byte[] b = new byte[2];
        for (int i = 0; i < 2; i++) {
            bb[index + i] = Integer.valueOf(temp & 0xff).byteValue(); // 将最高位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
    }

    public char getChar(byte[] b, int index) {
        int s = 0;
        if (b[index + 1] > 0)
            s += b[index + 1];
        else
            s += 256 + b[index];
        s *= 256;
        if (b[index] > 0)
            s += b[index + 1];
        else
            s += 256 + b[index];
        char ch = (char) s;
        return ch;
    }

    public void putFloat(byte[] bb, float x, int index) {
        // byte[] b = new byte[4];
        int l = Float.floatToIntBits(x);
        for (int i = 0; i < 4; i++) {
            bb[index + i] = Integer.valueOf(l).byteValue();
            l = l >> 8;
        }
    }

    public float getFloat(byte[] b, int index) {
        int l;
        l = b[index];
        l &= 0xff;
        l |= ((long) b[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) b[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }

    public static void putDouble(byte[] bb, double x, int index) {
        // byte[] b = new byte[8];
        long l = Double.doubleToLongBits(x);
        for (int i = 0; i < 4; i++) {
            bb[index + i] = Long.valueOf(l).byteValue();
            l = l >> 8;
        }
    }

    public double getDouble(byte[] b, int index) {
        long l;
        l = b[0];
        l &= 0xff;
        l |= ((long) b[1] << 8);
        l &= 0xffff;
        l |= ((long) b[2] << 16);
        l &= 0xffffff;
        l |= ((long) b[3] << 24);
        l &= 0xffffffffL;
        l |= ((long) b[4] << 32);
        l &= 0xffffffffffL;
        l |= ((long) b[5] << 40);
        l &= 0xffffffffffffL;
        l |= ((long) b[6] << 48);
        l &= 0xffffffffffffffL;
        l |= ((long) b[7] << 56);
        return Double.longBitsToDouble(l);
    }


    /**
     * @param result 返回结果
     * @param value  bitn-bitm位
     * @param offset 下标偏移位置
     * @return byte
     */
    private byte putBitValue(byte result, byte value, int offset) {
        value <<= offset;
        result = (byte) (value | result);
        return result;
    }


    /**
     * 节目属性组合一个byte
     *
     * @param isClose   是否关闭 0 不关闭，1 关闭
     * @param isLocking 是否锁定 0不锁定，1锁定
     * @param howPlay   如何播放 0正常播放，1使用定时播放， 2手动播放，3传感器触发播放， 4立即播放
     * @param playModel 1按次播放，2按时间长度播放， 3按天播放节目，4按周播放
     * @return byte 组合结果
     */
    public byte getProgramAttr(byte isClose, byte isLocking, byte howPlay, byte playModel) {
        byte result = 0;
        result = putBitValue(result, isClose, 0);
        result = putBitValue(result, isLocking, 1);
        result = putBitValue(result, howPlay, 2);
        result = putBitValue(result, playModel, 5);
        return result;
    }

    /**
     * 边框和背景数在同一个byte 中表示，bit0-bit3表示边框数，0不使用；bit4-bit7 表示背景个数，0不使用
     *
     * @param borderCount 边框数
     * @param bgCount     背景数
     * @return byte
     */

    public byte getProgramBorderAndBgCount(byte borderCount, byte bgCount) {
        byte result = 0;
        result = putBitValue(result, borderCount, 0);
        result = putBitValue(result, bgCount, 4);
        return result;
    }

    public int useOneHalfStorage(int value1, int value2) {
        int result = 0;
        result = putBitValueInt(result, value1, 0);
        result = putBitValueInt(result, value2, 16);
        return result;

    }

    public int putBitValueInt(int result, int value, int offset) {
        value <<= offset;
        result = (value | result);
        return result;
    }


    public int putBitValueInt(int value, int offset) {
        return putBitValueInt(0, value, offset);
    }


    /**
     * 取一个字节的start 到end 位 转换成十进制数
     *
     * @param src   一个字节数据 8bit
     * @param start bit起始位
     * @param end   bit 截止位
     * @return int 起始位到截止位的bit数 转换成十进制的值
     */
    public int getValueByIndex(byte src, int start, int end) {
        int value = 0;
        for (int i = end; i >= start; i--) {
            int x = (src >> i) & 0x1;
            System.out.print(x);
            value += x * Math.pow(2, i - start);
        }
        //        System.out.println("十进制数："+ (int)src +"二进制数："+byteToBit(src)+"取"+start+"-"+end+"位"+"转换成十进制值"+value);
        return value;
    }


    /**
     * 把byte转为字符串的bit
     *
     * @param b 需要处理的字节
     * @return string
     */
    public String byteToBit(byte b) {
        return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1) + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1) + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1) + (byte) ((b >> 1) & 0x1) + (byte) (b & 0x1);
    }


    private final char[] hexCode = "0123456789ABCDEF".toCharArray();

    public String printHexBinary(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
            r.append("  ");

            if ((i + 1) % 16 == 0) {
                r.append("\n");
            }
        }
        return r.toString();
    }


    public byte[] appendData(byte[] total, byte[] data) {
        if (total == null) {
            total = new byte[0];
        }
        ByteBuffer buffer = ByteBuffer.allocate(total.length + data.length);
        buffer.put(total);
        buffer.put(data);

        return buffer.array();
    }


    /**
     * byte[] 转int[]
     *
     * @param bytes
     * @return
     */

    public int[] bytesToInts(byte[] bytes) {
        int bytesLength = bytes.length;
        int[] ints = new int[bytesLength % 4 == 0 ? bytesLength / 4 : bytesLength / 4 + 1];
        int lengthFlag = 4;
        while (lengthFlag <= bytesLength) {
            ints[lengthFlag / 4 - 1] = (bytes[lengthFlag - 4] << 24) | (bytes[lengthFlag - 3] & 0xff) << 16 | (bytes[lengthFlag - 2] & 0xff) << 8 | (bytes[lengthFlag - 1] & 0xff);
            lengthFlag += 4;
        }
        for (int i = 0; i < bytesLength + 4 - lengthFlag; i++) {
            if (i == 0)
                ints[lengthFlag / 4 - 1] |= bytes[lengthFlag - 4 + i] << 8 * (bytesLength + 4 - lengthFlag - i - 1);
            else
                ints[lengthFlag / 4 - 1] |= (bytes[lengthFlag - 4 + i] & 0xff) << 8 * (bytesLength + 4 - lengthFlag - i - 1);
        }
        return ints;
    }

    /**
     * 将int 转换成byte[] 高字节在后
     *
     * @param value 被转换字节
     * @return byte[]
     */
    public byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        src[3] = (byte) ((value >> 24) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }


    public int byteArrayToInt(byte[] bArr) {
        if (bArr.length != 4) {
            return -1;
        }
        return (((bArr[3] & 0xff) << 24) | ((bArr[2] & 0xff) << 16) | ((bArr[1] & 0xff) << 8) | ((bArr[0] & 0xff)));
    }


    public String byteArrayToText(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append((char) b);
        }
        return sb.toString();
    }

    /**
     * 从起始位置到index 位置 截取字符数组
     *
     * @param src   原byte[] 数组
     * @param index 截取的长度
     * @return 新的byte[]
     */
    public byte[] getSubByteArray(byte[] src, int index) {
        byte[] subByteArray = new byte[index];
        System.arraycopy(src, 0, subByteArray, 0, subByteArray.length);
        return subByteArray;
    }


}
