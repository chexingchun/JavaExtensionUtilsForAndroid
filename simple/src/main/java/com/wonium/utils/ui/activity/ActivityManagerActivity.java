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

import android.app.Activity;
import android.databinding.DataBindingUtil;

import com.wonium.example.R;
import com.wonium.example.databinding.ActivityActivityManagerBinding;
import com.wonium.extension.utils.ActivityManagerUtil;
import com.wonium.extension.utils.StringUtil;
import com.wonium.extension.utils.ToastUtil;
/**
 * @ClassName: ActivityManagerActivity
 * @Description: 添加描述
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/20 14:12
 * @UpdateUser: 添加更新者
 * @UpdateDate:  2018/11/20 14:12
 * @UpdateDescription: 更新描述
 * @Version:
 */
public class ActivityManagerActivity extends BaseActivity {
    private ActivityActivityManagerBinding mBinding;

    @Override
    public void initView(int layoutResID) {
        mBinding = DataBindingUtil.setContentView(this, layoutResID);
        setSupportActionBar(mBinding.includeToolbarActivityManager.toolbar);
        mBinding.setTitle("ActivityManagerUtil");
    }

    @Override
    public void initListener() {
        mBinding.includeToolbarActivityManager.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        mBinding.includeToolbarActivityManager.toolbar.setNavigationOnClickListener(v -> finish());
        // 添加当前Actvity
        mBinding.btnAddActivity.setOnClickListener(v -> {
            ActivityManagerUtil.getInstance().addActivity(this);
            ToastUtil.INSTANCE.show(this, "添加一个activity");
        });

        // 移除最后一个activity
        mBinding.btnRemoveCurrentActivity.setOnClickListener(v -> ActivityManagerUtil.getInstance().finishActivity());
        // 移除所有activity
        mBinding.btnRemoveAllActivity.setOnClickListener(v -> ActivityManagerUtil.getInstance().finishAllActivity());
        // 移除给定的activity
        mBinding.btnRemoveActivityForParam.setOnClickListener(v -> ActivityManagerUtil.getInstance().finishActivity(this));
        // 根据activity 的class 移除
        mBinding.btnRemoveActivityByClassName.setOnClickListener(v->ActivityManagerUtil.getInstance().finishActivity(this.getClass()));
        // 获取当前的activity
        mBinding.btnGetCurrentActivity.setOnClickListener(v ->{
            Activity activity =ActivityManagerUtil.getInstance().currentActivity();
            ToastUtil.INSTANCE.show(this,"currentActivity->"+activity.getClass().getSimpleName());
        });
        // 获取activity栈的大小
        mBinding.btnShowStackSize.setOnClickListener(v -> {ToastUtil.INSTANCE.show(this,StringUtil.INSTANCE.valueOf(ActivityManagerUtil.getInstance().size()));});
        // APP 退出
        mBinding.btnAppExit.setOnClickListener(v -> ActivityManagerUtil.getInstance().appExit(this));
    }


    @Override
    public int getLayoutResId() {
        return R.layout.activity_activity_manager;
    }
}
