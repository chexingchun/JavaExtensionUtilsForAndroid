/*
 * Copyright  2018  WoNium,Joy,Lokiwife,JohnDwang
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

package com.wonium.utils.presenter.impl;

import com.wonium.extension.utils.StringUtil;
import com.wonium.utils.model.BaseModel;
import com.wonium.utils.model.StringModel;
import com.wonium.utils.model.impl.StringModelImpl;
import com.wonium.utils.presenter.StringPresenter;
import com.wonium.utils.ui.view.StringView;

/**
 * @ClassName: StringPresenterImpl.java
 * @Description: StringPresenter的实现类
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/17 22:04
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/17 22:04
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public class StringPresenterImpl implements StringPresenter {
    private StringView mStringView;
    private StringModel stringModel;
    public StringPresenterImpl(StringView stringView){
        this.mStringView =stringView;
        stringModel =new StringModelImpl();
    }

    @Override
    public void isNull(String character) {
        if (mStringView!=null){
            stringModel.isNull(character, new BaseModel.CallBack<String, String>() {
                @Override
                public void onSuccess(String result) {
                    mStringView.showTestIsNullMessage(result);
                }

                @Override
                public void onFailure(String error) {
                    mStringView.showTestIsNullMessage(error);
                }
            });
        }
    }

    @Override
    public void isEmpty(String charachter) {
        if (mStringView!=null){
            stringModel.isEmpty(charachter, new BaseModel.CallBack<String, String>() {
                @Override
                public void onSuccess(String result) {
                    mStringView.showTestIsEmptyMessage(result);
                }
            });
        }
    }

    @Override
    public  void valueOf(String charachter) {
        if (mStringView!=null){
            if (StringUtil.INSTANCE.isNull(charachter)) {
                mStringView.showTestValueOfMessage("请输入验证内容");
                return;
            }
            stringModel.valueOf(charachter, new BaseModel.CallBack<String, String>() {
                @Override
                public void onSuccess(String result) {
                    mStringView.showTestValueOfMessage(result);
                }
            });
        }
    }

    @Override
    public void getStringFormMap() {
        if (mStringView!=null){
            stringModel.getStringFromMap("double", "", new BaseModel.CallBack<String, String>() {
                @Override
                public void onSuccess(String result) {
                    mStringView.showTestGetStringFromMap(result);
                }
            });
        }
    }
}
