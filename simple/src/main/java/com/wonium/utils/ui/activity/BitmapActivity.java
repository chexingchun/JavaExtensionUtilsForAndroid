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
import android.graphics.Color;

import com.wonium.example.R;
import com.wonium.example.databinding.ActivityBitmapBinding;
import com.wonium.extension.utils.BitmapUtil;
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
        mBinding.includeToolbarBitmap.toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void initListener() {
        mBinding.btnCreateBitmap.setOnClickListener(v -> createBitmap());
        mBinding.btnImgToBitmap.setOnClickListener(v -> imgToBitmap());
        mBinding.btnBitmapSize.setOnClickListener(v -> getBitmapSize());
    }

    /**
     * 创建一个Bitmap
     */
    private  void createBitmap(){
        // 显示创建的Bitmap
        mBinding.imgShowCreateBitmap.setImageBitmap(BitmapUtil.INSTANCE.createBitmap(192,192,getResources().getColor(R.color.darkSalmon)));
    }
    private void imgToBitmap(){
        ToastUtil.INSTANCE.show(getContext(),"imgToBitmap");
        mBinding.imgToBitmap.setImageBitmap(BitmapUtil.INSTANCE.imgToBitmap(getContext(),R.drawable.img_wonium));
    }

    private void getBitmapSize(){
        Bitmap bitmap =BitmapUtil.INSTANCE.imgToBitmap(getContext(),R.drawable.img_wonium);
        mBinding.imgToBitmap.setImageBitmap(bitmap);
        mBinding.tvBitmapSize.setText(new StringBuilder().append("蜗牛图片转换成Bitmap的Size:\n").append(BitmapUtil.INSTANCE.getBitmapSize(bitmap)));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_bitmap;
    }
}
