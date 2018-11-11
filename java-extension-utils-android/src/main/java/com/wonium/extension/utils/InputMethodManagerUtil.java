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

    public static InputMethodManagerUtil getInstance(Context context) {
        InputMethodManagerUtil inputMethodManagerUtils = new InputMethodManagerUtil();
        inputMethodManagerUtils.inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputMethodManagerUtils;
    }

    public void toggleSoftInput() {
        if (inputMethodManager.isActive()) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void hideSoftInput(Activity activity) {
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
