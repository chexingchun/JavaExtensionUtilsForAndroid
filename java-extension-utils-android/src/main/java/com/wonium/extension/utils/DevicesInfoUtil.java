package com.wonium.extension.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 设备信息类
 * @author Wonium
 */
public class DevicesInfoUtil {
    private static final String TAG = "DevicesInfoUtil";

    /**
     * 获取MacAddr
     * @return macAddress
     */
    @SuppressLint("HardwareIds")
    public static String getMacAddress(Context context) {
        String macAddress = "02:00:00:00:00:00";
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                assert manager != null;
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

    private static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            // 列举
            Enumeration<NetworkInterface> netInterface = NetworkInterface.getNetworkInterfaces();
            // 是否还有元素
            while (netInterface.hasMoreElements()) {
                // 得到下一个元素
                NetworkInterface ni = netInterface.nextElement();
                // 得到一个ip地址的列举
                Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    ip = inetAddresses.nextElement();
                    if (!ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")){
                        break;
                    }else {
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
}

