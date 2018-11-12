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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wonium.extension.R;


/**
 * @ClassName: ToastUtil.java
 * @Description: Toast工具类
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/11 11:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/11 11:04
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public enum ToastUtil {
    /**
     * 实例对象
     */
    INSTANCE;
    private static boolean isToast = true;

    /**
     * 显示时间短
     *
     * @param context 上下文
     * @param content 显示内容
     */
    private void toastShort(Context context, CharSequence content) {
        if (isToast) {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示时间长
     *
     * @param context 上下文
     * @param content 显示内容
     */
    private void toastLong(Context context, CharSequence content) {
        if (isToast) {
            Toast.makeText(context, content, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 自定义持续时间
     *
     * @param context  上下文
     * @param content  显示内容
     * @param duration 持续时间
     */

    public void toastDuration(Context context, CharSequence content, int duration) {
        if (isToast) {
            Toast.makeText(context, content, duration).show();
        }
    }

    /**
     * 显示在UI 线程
     *
     * @param context  上下文
     * @param content  显示内容
     * @param duration 持续时间
     */
    public void toastUiThread(final Context context, final CharSequence content, final int duration) {
        if (isToast) {
            ((Activity) context).runOnUiThread(() -> Toast.makeText(context, content, duration).show());
        }
    }

    /**
     * 当内容大于6个文字， 显示的时间长
     * 否则显示时间短
     *
     * @param context 上下文
     * @param content 显示内容
     */
    public void show(Context context, CharSequence content) {
        if (content.length() > 6) {
            ToastUtil.INSTANCE.toastShort(context, content);
        } else {
            ToastUtil.INSTANCE.toastLong(context, content);
        }
    }

    /**
     * 显示自定义toast
     * g
     *
     * @param context  上下文
     * @param content  显示内容
     * @param duration 持续时间
     */
    public void showCustomToast(Context context, CharSequence content, int duration) {

        View view1 = LayoutInflater.from(context).inflate(R.layout.layout_toast_view, null, false);
        TextView textView = view1.findViewById(R.id.tv_toast_text);
        textView.setText(content);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        toast.setView(view1);
        toast.show();

    }


}
