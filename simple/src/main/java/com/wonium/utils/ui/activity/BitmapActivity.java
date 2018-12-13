/*
 * Copyright (c) 2018.  WoNium,Joy,Lokiwife,JohnDwang
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

package com.wonium.utils.ui.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;

import com.orhanobut.logger.Logger;
import com.wonium.example.R;
import com.wonium.example.databinding.ActivityBitmapBinding;
import com.wonium.extension.utils.BitmapUtil;
import com.wonium.extension.utils.ByteUtil;
import com.wonium.extension.utils.ThreadPoolUtil;
import com.wonium.extension.utils.ToastUtil;

/**
 * @ClassName: BitmapActivity.java
 * @Description: bitmapUtil 示例页面
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/20 21:59
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/20 21:59
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */

public class BitmapActivity extends BaseActivity {
    private ActivityBitmapBinding mBinding;

    @Override
    public void initView(int layoutResID) {
        mBinding = DataBindingUtil.setContentView(this, layoutResID);
        setSupportActionBar(mBinding.includeToolbarBitmap.toolbar);
        mBinding.includeToolbarBitmap.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        mBinding.setTitle(getResources().getString(R.string.item_bitmap));
        mBinding.includeToolbarBitmap.toolbar.setNavigationOnClickListener(v -> finish());
        flippingBitmap();

        //图片旋转
        Bitmap bitmap = BitmapUtil.INSTANCE.imgToBitmap(getContext(), R.drawable.img_wonium);
        mBinding.imgRotateBitmap.setImageBitmap(BitmapUtil.INSTANCE.rotateBitmap(bitmap,-90));
        // 打印RGBA数据
        byte[] bytes=BitmapUtil.INSTANCE.getPixels(bitmap);
        Logger.d("打印RGBA数据————>: "+ByteUtil.INSTANCE.printHexBinary(bytes));
        // 打印RGB数据 24位
         byte[] bytes24=BitmapUtil.INSTANCE.getRGBDataFormat24(bitmap);
        Logger.d("打印RGB数据24位————>: "+ByteUtil.INSTANCE.printHexBinary(bytes24));


    }

    @Override
    public void initListener() {
        // 创建一个bitmap
        mBinding.btnCreateBitmap.setOnClickListener(v -> createBitmap());
        // img 转bitmap
        mBinding.btnImgToBitmap.setOnClickListener(v -> imgToBitmap());
        // 获取Bitmap的大小
        mBinding.btnBitmapSize.setOnClickListener(v -> getBitmapSize());
        // Bitmap To Bytes
        mBinding.btnBitmapToBytes.setOnClickListener(v -> bitmapToBytes());
        mBinding.btnGetBitmapByPath.setOnClickListener(v -> getBitmapFromPath());


    }

    /**
     * 创建一个Bitmap
     */
    private void createBitmap() {
        // 显示创建的Bitmap
        mBinding.imgShowCreateBitmap.setImageBitmap(BitmapUtil.INSTANCE.createBitmap(192, 192, getResources().getColor(R.color.darkSalmon)));

    }

    private void imgToBitmap() {
        ToastUtil.INSTANCE.show(getContext(), "imgToBitmap");
        mBinding.imgToBitmap.setImageBitmap(BitmapUtil.INSTANCE.imgToBitmap(getContext(), R.drawable.img_wonium));
    }

    private void getBitmapSize() {
        Bitmap bitmap = BitmapUtil.INSTANCE.imgToBitmap(getContext(), R.drawable.img_wonium);
        mBinding.imgToBitmap.setImageBitmap(bitmap);
        mBinding.tvBitmapSize.setText(new StringBuilder().append("蜗牛图片转换成Bitmap的Size:\n").append(BitmapUtil.INSTANCE.getBitmapSize(bitmap)));
    }

    private void bitmapToBytes() {
        ToastUtil.INSTANCE.show(getContext(), "查看Log");
        Bitmap bitmap = BitmapUtil.INSTANCE.imgToBitmap(getContext(), R.drawable.img_wonium);
        Logger.d(ByteUtil.INSTANCE.printHexBinary(BitmapUtil.INSTANCE.bitmapToByte(bitmap)));
    }

    private void getBitmapFromPath() {
        String path = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542904680889&di=f436ad37f1dd8cacdac4c4ea138f685d&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D8987b62f0055b31988f48a362bc0e853%2Feac4b74543a98226e9b8d1098082b9014a90eba7.jpg";
        ThreadPoolUtil.INSTANCE.execute(()->{
            Bitmap bitmap =BitmapUtil.INSTANCE.getBitmapByPath(path);
            if (bitmap!=null){
                runOnUiThread(() -> mBinding.imgDisplayBitmapFormPath.setImageBitmap(bitmap));
            }
        });
    }

    /**
     * 图片反转
     */
    private void flippingBitmap(){
        Bitmap bitmap = BitmapUtil.INSTANCE.imgToBitmap(getContext(), R.drawable.img_wonium);
      mBinding.btnFlippingBitmap.setOnClickListener(v -> {
          mBinding.imgFlippingBitmap.setImageBitmap(BitmapUtil.INSTANCE.flippingBitmap(bitmap));
      });
    }


    @Override
    public int getLayoutResId() {
        return R.layout.activity_bitmap;
    }
}
