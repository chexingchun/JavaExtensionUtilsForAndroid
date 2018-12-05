/*
 * Copyright  2018,  WoNium,Joy,Lokiwife,JohnDwang
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

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ClassName: BlueToothUtil
 * @Description: 匹配蓝牙工具类
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/12/5 21:02
 * @UpdateUser: update user
 * @UpdateDate: 2018/12/5 21:02
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public enum BlueToothUtil {

    /**
     * 实例对象
     */
    INSTANCE;


    /**
     * 与设备配对 参考源码：platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    public boolean createBond(Class btClass, BluetoothDevice btDevice) throws Exception {
        Method createBondMethod = btClass.getMethod("createBond");

        return (Boolean) createBondMethod.invoke(btDevice);
    }

    /**
     * 与设备解除配对 参考源码：platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    public boolean removeBond(Class<?> btClass, BluetoothDevice btDevice) throws Exception {
        Method removeBondMethod = btClass.getMethod("removeBond");
        return (Boolean) removeBondMethod.invoke(btDevice);
    }

    public boolean setPin(Class<? extends BluetoothDevice> btClass, BluetoothDevice btDevice, String str) {
        try {
            Method removeBondMethod = btClass.getDeclaredMethod("setPin", byte[].class);
            Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice, new Object[]{str.getBytes()});
            Log.e("returnValue", "" + returnValue);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return true;

    }

    /**
     * 取消用户输入
     *
     * @param btClass
     * @param device
     * @return
     * @throws Exception
     */
    public boolean cancelPairingUserInput(Class<?> btClass, BluetoothDevice device) throws Exception {
        Method createBondMethod = btClass.getMethod("cancelPairingUserInput");
        return (Boolean) createBondMethod.invoke(device);
    }

    /**
     * 取消配对
     *
     * @param btClass BluetoothDevice instance   btDevice.getClass()
     * @param device
     * @return
     * @throws Exception
     */
    public boolean cancelBondProcess(Class<?> btClass, BluetoothDevice device) throws Exception {
        Method createBondMethod = btClass.getMethod("cancelBondProcess");
        return (Boolean) createBondMethod.invoke(device);
    }

    /**
     * 确认配对
     *
     * @param btClass
     * @param device
     * @param isConfirm
     * @throws Exception
     */

    public void setPairingConfirmation(Class<?> btClass, BluetoothDevice device, boolean isConfirm) throws Exception {
        Method setPairingConfirmation = btClass.getDeclaredMethod("setPairingConfirmation", boolean.class);
        setPairingConfirmation.invoke(device, isConfirm);
    }


    /**
     * @param clsShow
     */
    public void printAllInform(Class clsShow) {
        try {
            // 取得所有方法
            Method[] hideMethod = clsShow.getMethods();
            int i = 0;
            for (; i < hideMethod.length; i++) {
                Log.e("method name", hideMethod[i].getName() + ";and the i is:" + i);
            }
            // 取得所有常量
            Field[] allFields = clsShow.getFields();
            for (i = 0; i < allFields.length; i++) {
                Log.e("Field name", allFields[i].getName());
            }
        } catch (SecurityException e) {

            e.printStackTrace();
        } catch (IllegalArgumentException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}  
