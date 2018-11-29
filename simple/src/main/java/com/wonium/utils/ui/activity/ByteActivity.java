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
import android.view.View;

import com.orhanobut.logger.Logger;
import com.wonium.example.R;
import com.wonium.example.databinding.ActivityByteBinding;
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
 * @UpdateDescription: 更新描述
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
    }

    @Override
    public void initListener() {
        mBinding.includeToolbarByte.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManagerUtil.getInstance().finishActivity();
            }
        });
        mBinding.btnByteArrayToShort.setOnClickListener(v -> {
            byte[] bytes = new byte[2];
            bytes[0] = 1;
            bytes[1] = 2;
            Logger.d("bytes->short:" + ByteUtil.INSTANCE.byteArrayToShort(bytes) + "\n");
        });
        mBinding.btnShortToByteArray.setOnClickListener(v -> Logger.d("short->bytes:" + ByteUtil.INSTANCE.printHexBinary(ByteUtil.INSTANCE.shortToByteArray((short)
                513))));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_byte;
    }
}
