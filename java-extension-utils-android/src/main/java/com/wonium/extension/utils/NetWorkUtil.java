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

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;


/**
 * @ClassName: NetWorkUtil.java
 * @Description: 网络工具包
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/11 14:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/11 14:47
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public enum  NetWorkUtil {
    /**
     * 实例对象
     */
    INSTANCE;

    /**
     * 当前连接时wifi
     */

    public static final int NETTYPE_WIFI = 1;
    /**
     * 当前连接时cmnap
     */
    public static final int NETTYPE_CMWAP = 2;
    /**
     * 当前链接是cmnet
     */
    public static final int NETTYPE_CMNET = 3;
    /**
     * 检测网络是否连接
     * @param context 上下文
     * @return  连接 true 断网 false
     */
    public  boolean isNetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null;
        }

        return false;
    }

    /**
     * 检测wifi是否连接
     * @param context 上下文
     * @return
     */
    public boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }

    /**
     * 检测3G是否连接
     * @param context 上下文
     * @return
     */
    public boolean is3GConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        }
        return false;
    }

    /**
     * 检测GPS是否打开
     * @return
     */
    public  boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        assert lm != null;
        List<String> accessibleProviders = lm.getProviders(true);
        for (String name : accessibleProviders) {
            if ("gps".equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断网络是否可用
     */
    public  boolean isNetworkAvailable(Context context) {

        boolean isConnect = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                //获取网络连接管理的对象
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        isConnect = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnect;
    }



    /**
     * 获取网络连接状态
     *
     * @param context
     * @return
     */
    public  int getNetWorkType(Context context) {
        int netType = 0;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null) {
            return netType;
        }

        int networkType = info.getType();
        if (networkType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = info.getExtraInfo();
            if (StringUtil.INSTANCE.isNull(extraInfo)) {
                if ("cmnet".equals(extraInfo.toLowerCase())) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (networkType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

    /**
     * 获取 IP 地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param useIPv4 是否用 IPv4
     * @return IP 地址
     */
    public  String getIPAddress(final boolean useIPv4) {
        try {
            for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces(); nis.hasMoreElements(); ) {
                NetworkInterface ni = nis.nextElement();
                // 防止小米手机返回 10.0.2.15
                if (!ni.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
                        if (useIPv4) {
                            if (isIPv4) {
                                return hostAddress;
                            }
                        } else {
                            if (!isIPv4) {
                                int index = hostAddress.indexOf('%');
                                return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }


}
