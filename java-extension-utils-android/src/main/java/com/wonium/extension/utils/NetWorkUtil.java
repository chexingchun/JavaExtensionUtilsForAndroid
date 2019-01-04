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
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


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
public enum NetWorkUtil {


    /**
     * 实例对象
     */
    INSTANCE;

    /**
     * 当前连接时wifi
     */

    public final int NETTYPE_WIFI = 1;
    /**
     * 当前连接时cmnap
     */
    public final int NETTYPE_CMWAP = 2;
    /**
     * 当前链接是cmnet
     */
    public final int NETTYPE_CMNET = 3;


    /**
     * 检测网络是否连接
     *
     * @param context 上下文
     * @return 连接 true 断网 false
     */
    public boolean isNetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null;
        }

        return false;
    }

    /**
     * 检测wifi是否连接
     *
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
     *
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
     *
     * @param context 上下文
     * @return true 已开启gps false 已关闭gps
     */
    public boolean isGpsEnabled(Context context) {
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
    public boolean isNetworkAvailable(Context context) {

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
     * @param context 上下文
     * @return 网络连接状态 {NETTYPE_CMNET，NETTYPE_CMWAP，NETTYPE_WIFI}
     */
    public int getNetWorkType(Context context) {
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
     * 返回手机的网络连接类型 2G，3G，4G，WIFI
     *
     * @param context 上下文
     * @return tel.getNetworkType
     */
    public int getNetworkType(Context context) {
        TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tel.getNetworkType();
    }

    /**
     * 获取 IP 地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param useIPv4 是否用 IPv4
     * @return IP 地址
     */
    public String getIPAddress(final boolean useIPv4) {
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

    /**
     * 获取网络运营商的中文名
     *
     * @param context 上下文
     * @return 网络运营商的中文名
     */
    public String getNetWorkOperatorName(Context context) {
        Map<String, String> map = new TreeMap<>();
        map.put("46000", "中国移动");
        map.put("46001", "中国联通");
        map.put("46003", "中国电信");
        String operatorCode = getNetworkOperatorCode(context);
        for (String s : map.keySet()) {
            if (operatorCode.equals(s)) {
                return map.values().iterator().next();
            }
        }
        return null;
    }

    /**
     * 获取网络运营商的编号
     *
     * @param context 上下文
     * @return 网络运营商的编号
     */
    public String getNetworkOperatorCode(Context context) {//得到当前电话卡的归属
        TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tel.getNetworkOperator();
    }


    /**
     * 获取SSID
     *
     * @param activity 上下文
     * @return WIFI ssid
     */
    public String getWifiSsid(Activity activity) {
        String ssid = "unknown id";
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
            WifiManager mWifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            assert mWifiManager != null;
            WifiInfo info = mWifiManager.getConnectionInfo();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return info.getSSID();
            } else {
                return info.getSSID().replace("\"", "");
            }
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {

            ConnectivityManager connManager = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connManager != null;
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo.isConnected()) {
                if (networkInfo.getExtraInfo() != null) {
                    return networkInfo.getExtraInfo().replace("\"", "");
                }
            }
        }
        return ssid;
    }


    /**
     * 获得当前网络的DBM 等级
     *
     * @param context 上下文
     * @param signal  信号值
     * @return dbm 等级
     */
    private int getNetDbmLevel(Context context, int signal) {
        String sMark = getNetworkOperatorCode(context);
        int level = 0;

        if (sMark.equals("46003")) {//电信3g信号强度的分类,可以按照ui自行划分等级
            if (signal >= -65) {
                level = 5;
            } else if (signal >= -75) {
                level = 4;
            } else if (signal >= -85) {
                level = 3;
            } else if (signal >= -95) {
                level = 2;
            } else if (signal >= -105) {
                level = 1;
            }
        }
        if (sMark.equals("46001")) {//联通3g信号划分
            if (signal >= -75) {
                level = 5;
            } else if (signal >= -80) {
                level = 4;
            } else if (signal >= -85) {
                level = 3;
            } else if (signal >= -95) {
                level = 2;
            } else if (signal >= -100) {
                level = 1;
            } else {
                level = 0;
            }
        }
        if (sMark.equals("46000")) {//移动信号的划分,这个不是很确定是2g还是3g
            if (signal <= 2 || signal == 99) {
                level = 0;
            } else if (signal >= 12) {
                level = 5;
            } else if (signal >= 10) {
                level = 4;
            } else if (signal >= 8) {
                level = 3;
            } else if (signal >= 5) {
                level = 2;
            } else {
                level = 1;
            }
        }
        return level;
    }


    /**
     * 得到当前的手机蜂窝网络信号强度
     * 获取LTE网络和3G/2G网络的信号强度的方式有一点不同，
     * LTE网络强度是通过解析字符串获取的，
     * 3G/2G网络信号强度是通过API接口函数完成的。
     * asu 与 dbm 之间的换算关系是 dbm=-113 + 2*asu
     */
    public int getCurrentNetWorkDBM(final Context context, SignalStrength signalStrength) {
        int signal = 0;
        String signalInfo = signalStrength.toString();
        String[] params = signalInfo.split(" ");
        int netWorkType = getNetworkType(context);
        if (netWorkType == TelephonyManager.NETWORK_TYPE_LTE) {
            //4G网络 最佳范围   >-90dBm 越大越好
            signal = Integer.parseInt(params[11]);
        } else if (netWorkType == TelephonyManager.NETWORK_TYPE_HSDPA
                || netWorkType == TelephonyManager.NETWORK_TYPE_HSPA
                || netWorkType == TelephonyManager.NETWORK_TYPE_HSUPA
                || netWorkType == TelephonyManager.NETWORK_TYPE_UMTS ) {
            //3G网络最佳范围  >-90dBm  越大越好  ps:中国移动3G获取不到  返回的无效dbm值是正数（85dbm）
            //在这个范围的已经确定是3G，但不同运营商的3G有不同的获取方法，故在此需做判断 判断运营商与网络类型的工具类在最下方
            String operatorCode = getNetworkOperatorCode(context);//获取当前运营商
            switch (operatorCode) {
                case "46000":
                    break;
                case "46001":
                    signal = signalStrength.getCdmaDbm();

                    break;
                case "46003":
                    signal = signalStrength.getEvdoDbm();
                    break;
            }

        } else {
            //2G网络最佳范围>-90dBm 越大越好
            int asu = signalStrength.getGsmSignalStrength();
            signal = -113 + 2 * asu;
        }
        return signal;
    }

}
