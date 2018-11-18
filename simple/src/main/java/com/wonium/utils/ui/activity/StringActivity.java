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

import android.databinding.DataBindingUtil;

import com.orhanobut.logger.Logger;
import com.wonium.example.R;
import com.wonium.example.databinding.ActivityStringBinding;
import com.wonium.extension.utils.StringUtil;
import com.wonium.utils.presenter.StringPresenter;
import com.wonium.utils.presenter.impl.StringPresenterImpl;
import com.wonium.utils.ui.view.StringView;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName: StringActivity.java
 * @Description: 类描述
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/17 15:27
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/17 15:27
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public class StringActivity extends BaseActivity implements StringView {
    private ActivityStringBinding mBinding;
    private StringPresenter mPresenter;
    private StringBuilder builder =new StringBuilder();
    @Override
    public void initView(int layoutResID) {
        mBinding = DataBindingUtil.setContentView(this, layoutResID);
        setSupportActionBar(mBinding.includeToolbar.toolbar);
        mBinding.setToolbarTitle("StringUtil");
        mPresenter = new StringPresenterImpl(this);
        try {
            builder.append("字符串重置 wonium-->").append(StringUtil.INSTANCE.reverseString("wonium")).append("\n")
            .append("格式化10以内的整数 例如 5-->").append(StringUtil.INSTANCE.formatInt(5)).append("\n")
            .append("更改字符集 例如 wonium-->").append(StringUtil.INSTANCE.changeCharSet("wonium","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Logger.e("更改字符集方法需要处理 UnsupportedEncodingException异常");
        }


        mBinding.tvExample.setText(StringUtil.INSTANCE.isEmpty(builder.toString()));
    }

    @Override
    public void initListener() {
        mBinding.btnIsNull.setOnClickListener(v -> mPresenter.isNull(mBinding.editIsNull.getText().toString().trim()));
        mBinding.btnIsEmpty.setOnClickListener(v -> mPresenter.isEmpty(mBinding.editIsEmpty.getText().toString().trim()));
        mBinding.btnValueOf.setOnClickListener(v -> mPresenter.valueOf(mBinding.editValueOf.getText().toString().trim()));
        mBinding.btnGetStringFromMap.setOnClickListener(v -> mPresenter.getStringFormMap());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_string;
    }

    @Override
    public void showTestIsNullMessage(String message) {
        mBinding.setIsNull(message);
    }

    @Override
    public void showTestIsEmptyMessage(String message) {
        mBinding.setIsEmpty(message);
    }

    @Override
    public void showTestValueOfMessage(String message) { mBinding.setValueOf(message); }

    @Override
    public void showTestGetStringFromMap(String message) {
        mBinding.setGetStringFromMap(message);
    }
}
