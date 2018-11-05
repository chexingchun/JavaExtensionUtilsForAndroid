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
 * Created by haiquan on 2016/6/1.
 * Email jsxzfxhhq@qq.com
 */
public enum ToastUtil {
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
    public static void showCustomToast(Context context, CharSequence content, int duration) {

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
