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

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;

import com.wonium.example.R;
import com.wonium.example.databinding.ActivityDeviceBinding;
import com.wonium.extension.utils.DateUtil;
import com.wonium.extension.utils.DeviceUtil;
import com.wonium.extension.utils.StringUtil;
import com.wonium.extension.utils.ToastUtil;

import ru.alexbykov.nopermission.PermissionHelper;

public class DeviceActivity extends BaseActivity {
    private ActivityDeviceBinding mBinding;
    private PermissionHelper mPermissionHelper;
    @Override
    public void initView(int layoutResID) {
        mBinding = DataBindingUtil.setContentView(this, layoutResID);
        setSupportActionBar(mBinding.includeLayoutDeviceToolbar.toolbar);
        mBinding.setTitle(getResources().getString(R.string.item_device));
        mPermissionHelper =new PermissionHelper(this);
    }

    @Override
    public void initListener() {
        mBinding.btnDeviceInfo.setOnClickListener(v->getDeviceInfo());
    }

    private  void getDeviceInfo(){

        mPermissionHelper.check(Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_SMS,Manifest.permission.READ_PHONE_NUMBERS)
                .onSuccess(this::onSuccess).onDenied(this::onDenied).onNeverAskAgain(this::onNeverAskAgain).run();

    }
    private void onSuccess(){
        StringBuilder builder =new StringBuilder();
        builder.append("phone number:")
                .append(StringUtil.INSTANCE.isEmpty(DeviceUtil.INSTANCE.getPhoneNumber(getContext())))
                .append("\nmac addr:")
                .append(DeviceUtil.INSTANCE.getMacAddress(this))
                .append("\nbrand:")
                .append(StringUtil.INSTANCE.isEmpty(DeviceUtil.INSTANCE.getPhoneBrand()))
                .append("\ntime:").append(DateUtil.INSTANCE.getDateTimeFromMillis(System.currentTimeMillis()));
        mBinding.tvDeviceResult.setText(builder);
    }
    private void onDenied(){
        ToastUtil.INSTANCE.show(this,"用户拒绝");
    }
    private void onNeverAskAgain(){
        ToastUtil.INSTANCE.show(this,"onNeverAskAgain");
    }


    @Override
    public int getLayoutResId() {
        return R.layout.activity_device;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}
