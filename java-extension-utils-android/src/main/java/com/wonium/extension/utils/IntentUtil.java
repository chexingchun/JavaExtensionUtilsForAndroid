package com.wonium.extension.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

/**
 * @ClassName: IntentUtil.java
 * @Description: 跳转方法
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/11 20:16
 * @UpdateUser: updateUser
 * @UpdateDate: 2018/11/11 20:16
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public enum IntentUtil {
    /**
     * 实例对象
     */
    INSTANCE;

    /**
     * 跳转到拨号页面
     * @param context
     * @param phone
     */
    public void callPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.startActivity(intent);
    }
}
