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


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @ClassName: DeviceUtil.java
 * @Description: 类描述
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/11 20:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/11 20:12
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public enum DeviceUtil {
    /**
     * 实例对象
     */
    INSTANCE;


    /**
     * 获取设备IMEI
     * @param activity
     * @return
     */

    @SuppressLint("MissingPermission")
    public  String getDeviceIMEI(Activity activity) {
        TelephonyManager manager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        assert manager != null;
        return manager.getDeviceId();
    }

    /**
     * 获取版本号
     * @param context
     * @return
     */
    public  int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     *获取版本名
     * @param context
     * @return 版本名称
     */
    public  String getVersionName(Context context){
        PackageManager manager =context.getPackageManager();
        try {
            PackageInfo info =manager.getPackageInfo(context.getPackageName(),0);
            return  info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0";
    }
    /**
     * 获取MacAddr
     *
     * @return macAddress
     */

    @SuppressLint("HardwareIds")
    public String getMacAddress(Context context) {
        String macAddress = "00:00:00:00:00:00";
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if (manager != null) {
                    WifiInfo info = manager.getConnectionInfo();
                    if (info != null) {
                        macAddress = info.getMacAddress();
                    }
                }
            } else {
                InetAddress ip = getLocalInetAddress();
                byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
                StringBuilder buffer = new StringBuilder();
                for (int i = 0; i < b.length; i++) {
                    if (i != 0) {
                        buffer.append(':');
                    }
                    String str = Integer.toHexString(b[i] & 0xFF);
                    buffer.append(str.length() == 1 ? 0 + str : str);
                }
                macAddress = buffer.toString().toUpperCase();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return macAddress.toLowerCase();
    }

    private InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            // 列举
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            // 是否还有元素
            while (networkInterfaces.hasMoreElements()) {
                // 得到下一个元素
                NetworkInterface ni = networkInterfaces.nextElement();
                // 得到一个ip地址的列举
                Enumeration<InetAddress> enIp = ni.getInetAddresses();
                while (enIp.hasMoreElements()) {
                    ip = enIp.nextElement();
                    if (!ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                        break;
                    } else {
                        ip = null;
                    }
                }
                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 获取手机号
     * @param context 上下文
     * @return 手机号
     */
    @SuppressLint ({"MissingPermission", "HardwareIds"})
    public String getPhoneNumber(Context context){
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        assert manager != null;
        return  manager.getLine1Number();
    }

    /**
     * 获取手机品牌
     * @return Build.BRAND
     */
    public String getBrand(){
        return  Build.BRAND;
    }

    /**
     * 获取手机型号
     * @return Build.MODEL
     */
    public String getModel(){
        return Build.MODEL;
    }

    /**
     * 获取手机唯一的id
     * @return Settings.Secure.Android_ID
     */
    public String getAndroidId(Context context){
        return  Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
    }



}

