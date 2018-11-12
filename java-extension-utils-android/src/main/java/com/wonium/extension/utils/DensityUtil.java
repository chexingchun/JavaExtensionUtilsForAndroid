package com.wonium.extension.utils;

import android.content.Context;

/**
 * @ClassName: DensityUtil.java
 * @Description: 尺寸工具包
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/11 19:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/11 19:57
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public enum  DensityUtil {
    /**
     * 实例对象
     */
    INSTANCE;

    /**
    * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param context 上下文
     * @param dpValue dp值
    */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * @param context  上下文
     * @param pxValue 像素值
     * @return diP值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) ((spValue-0.5f) * fontScale);
    }

    /**
     * 像素转换成sp
     * @param context 上下文
     * @param pxValue 像素值
     * @return sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
