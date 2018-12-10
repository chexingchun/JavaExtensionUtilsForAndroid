/*
 * Copyright  2018.  wonium
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
 *
 */

package com.wonium.utils.ui.activity;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.wonium.example.R;
import com.wonium.example.databinding.ActivityByteBinding;
import com.wonium.extension.Endian;
import com.wonium.extension.utils.ActivityManagerUtil;
import com.wonium.extension.utils.ByteUtil;

/**
 * @ClassName: ByteActivity
 * @Description: 添加描述
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/29 15:00
 * @UpdateUser: 添加更新者
 * @UpdateDate: 2018/11/29 15:00
 * @UpdateDescription: 更新描述 二进制
 * @Version:
 */
public class ByteActivity extends BaseActivity {
    private ActivityByteBinding mBinding;

    @Override
    public void initView(int layoutResID) {
        mBinding = DataBindingUtil.setContentView(this, layoutResID);
        mBinding.includeToolbarByte.setTitle(getString(R.string.item_byte));
        setSupportActionBar(mBinding.includeToolbarByte.toolbar);
        mBinding.includeToolbarByte.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_24dp));


        // getBitValue
        byte value= (byte) 234;
        Logger.d("234 convert binary" +ByteUtil.INSTANCE.byteToBit(value));
        Logger.d("获取value index 为4的值"+ByteUtil.INSTANCE.getBitValue(value,4));

        //byteArrayToShort 如果你不了解大小端 请参考 https://blog.wonium.com/archives/115/
        byte[] bytes=new byte[2];
        bytes[0]=8;
        bytes[1]=1;
        Logger.d("bytesArrayToShort Big-->"+ByteUtil.INSTANCE.byteArrayToShort(bytes,Endian.BIG));
        Logger.d("byteArrayToShort--Little>"+ByteUtil.INSTANCE.byteArrayToShort(bytes,Endian.LITTLE));

        //printByteArrayToBinary 如果你不了解大小端 请参考 https://blog.wonium.com/archives/115/
        Logger.d("shortToByteArray Big-Endian-->"+ByteUtil.INSTANCE.printByteArrayToBinary(ByteUtil.INSTANCE.shortToByteArray((short) 2049,Endian.BIG)));
        Logger.d("shortToByteArray Little-Endian-->"+ByteUtil.INSTANCE.printByteArrayToBinary(ByteUtil.INSTANCE.shortToByteArray((short) 2049,Endian.LITTLE)));


        // byteArrayToInt
        byte[] byteInt=new byte[4];
        byteInt[0]=5;
        byteInt[1]=1;
        byteInt[2]=1;
        byteInt[3]=64;
        Logger.d("byteArrayToInt Big-Endian-->"+ByteUtil.INSTANCE.byteArrayToInt(byteInt,Endian.BIG));
        Logger.d("byteArrayToInt Little-Endian-->"+ByteUtil.INSTANCE.byteArrayToInt(byteInt,Endian.LITTLE));

        // intToByteArray 83951936
        Logger.d("intToByteArray Big-Endian-->"+ByteUtil.INSTANCE.printByteArrayToBinary(ByteUtil.INSTANCE.intToByteArray(83951936,Endian.BIG)));
        Logger.d("intToByteArray Little-Endian-->"+ByteUtil.INSTANCE.printByteArrayToBinary(ByteUtil.INSTANCE.intToByteArray(83951936,Endian.LITTLE)));


        // byteArrayToFloat
        byte[] byteFloat=new byte[4];
        byteFloat[0]=1;
        byteFloat[1]=0;
        byteFloat[2]=0;
        byteFloat[3]=0;

        Logger.d("byteArrayToFloat Big-Endian-->"+ByteUtil.INSTANCE.byteArrayToFloat(byteFloat,Endian.BIG));
        Logger.d("byteArrayToFloat Little-Endian-->"+ByteUtil.INSTANCE.byteArrayToFloat(byteFloat,Endian.LITTLE));


    }

    @Override
    public void initListener() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_byte;
    }
}
