/*
 * Copyright  2018.  wonium
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
 *
 */

package com.wonium.extension.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: Md5Util
 * @Description: MD5 工具
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/15 9:16
 * @UpdateUser: 添加更新者
 * @UpdateDate: 2018/11/15 9:16
 * @UpdateDescription: 更新描述
 * @Version:
 */
public enum Md5Util {
    /**
     * 实例对象
     */
    INSTANCE;

    /**
     * 字符串MD5加密
     * @param text 被加密的字符串
     * @return 加密后的字符串
     */
    public String toMD5Text(String text) {
        byte[] bytes;
        try {
            bytes = MessageDigest.getInstance("md5").digest(text.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("not found this md5 method！");
        }
        StringBuilder md5code = new StringBuilder(new BigInteger(1, bytes).toString(16));
        // 补齐32位
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code.insert(0, "0");
        }
        return md5code.toString();
    }

    public static void main(String[] args) {
        System.out.print(Md5Util.INSTANCE.toMD5Text("wonium"));
    }
}
