/*
 * Copyright  2018  WoNium, Joy, Lokiwife.
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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wonium.example.R;
import com.wonium.extension.utils.ActivityManagerUtil;
import com.wonium.extension.utils.StatusBarUtil;

/**
 * @ClassName: BaseActivity.java
 * @Description: activity 公共基类
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/17 10:08
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/17 10:08
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityManagerUtil.getInstance().addActivity(this);
        initView(getLayoutResId());
        initListener();
        setStatusBar();
    }

    public Context getContext() {
        return mContext;
    }
    /**
     * 设置状态栏
     */
    protected void setStatusBar() {
        try {
            StatusBarUtil.INSTANCE.setColor(this, getStatusColor(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected int getStatusColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
    /**
     * 初始化View
     *
     * @param layoutResID 布局资源ID
     */
    public abstract void initView(int layoutResID);

    /**
     * 初始化监听事件
     */
    public abstract void initListener();

    /**
     * 获取资源文件Id
     * @return
     */
    public abstract int getLayoutResId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerUtil.getInstance().finishActivity(this);
    }
}
