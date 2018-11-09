package com.wonium.extension.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


public enum DeviceUtil {

    INSTANCE;

    /**
     * 获取MacAddr
     *
     * @return macAddress
     */
    @SuppressLint ("HardwareIds")
    public String getMacAddress(Context context) {
        String macAddress = "00:00:00:00:00:00";

        try {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = manager.getConnectionInfo();
                macAddress = info.getMacAddress();

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
        return macAddress;
    }

    private InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            // 列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {// 是否还有元素
                NetworkInterface ni = en_netInterface.nextElement();// 得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();// 得到一个ip地址的列举

                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && !ip.getHostAddress().contains(":"))
                        break;
                    else
                        ip = null;
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
     * 获取SSID
     * @param context 上下文
     * @return  WIFI 的SSID
     */
    public String getWIFISSID(Context context) {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {

            WifiManager mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = mWifiManager.getConnectionInfo();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return info.getSSID();
            } else {
                return info.getSSID().replace("\"", "");
            }
        } else if (Build.VERSION.SDK_INT==Build.VERSION_CODES.O_MR1){
            ConnectivityManager connManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo.isConnected()) {
                if (networkInfo.getExtraInfo()!=null){
                    return networkInfo.getExtraInfo().replace("\"","");
                }
            }
        } else if (Build.VERSION.SDK_INT==Build.VERSION_CODES.P){
            // 需要请求权限。
            WifiManager mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = mWifiManager.getConnectionInfo();
            return info.getSSID();
        }
        return "";
    }
}


