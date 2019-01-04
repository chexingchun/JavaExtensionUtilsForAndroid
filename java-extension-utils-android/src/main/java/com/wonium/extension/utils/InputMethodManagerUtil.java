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

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * @ClassName: InputMethodManagerUtil.java
 * @Description: 软键盘工具包
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/11 20:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/11 20:24
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */

public class InputMethodManagerUtil {

    private InputMethodManager inputMethodManager;
    private static InputMethodManagerUtil mInstance;

    /**
     * 返回一个对象
     *
     * @param context
     * @return
     */
    public static InputMethodManagerUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (InputMethodManagerUtil.class) {
                if (mInstance == null) {
                    mInstance = new InputMethodManagerUtil();
                }
            }
        }
        mInstance.inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return mInstance;
    }

    /**
     * 打开软键盘
     */
    public void toggleSoftInput() {
        if (inputMethodManager.isActive()) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param activity 软键盘所在的页面
     */
    public void hideSoftInput(Activity activity) {
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
