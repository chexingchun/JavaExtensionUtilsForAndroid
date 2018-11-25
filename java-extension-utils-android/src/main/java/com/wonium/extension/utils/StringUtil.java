/*
 * Copyright  2018  WoNium, Joy, Lokiwife.
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

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @ClassName: StringUtil.java
 * @Description: String 工具类
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/11 15:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/11 15:03
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public enum StringUtil {
    /**
     * 实例对象
     */
    INSTANCE;



    public boolean isNull(CharSequence text) {
        return TextUtils.isEmpty(text);
    }

    public String isEmpty(String text) {
        if (text == null || "".equals(text) || text.length() == 0) {
            return "";
        } else {
            return text;
        }
    }

    public <T> String valueOf(T value) {
        return String.valueOf(value);
    }

    /**
     * 从Map集合中获取给定Key对应的Value
     * @param map
     * @param key
     * @param defaultValue
     * @return
     */
    public String getStringFromMap(Map map, String key, String defaultValue) {
        Object obj = map.get(key);
        return obj == null ? defaultValue : (obj instanceof Number && Pattern.matches("^[-\\+]?[\\d]*\\.0*$",obj.toString()) ? String.valueOf(Long.valueOf(((Number) obj).longValue())) : obj.toString());
    }
    /**
     * 反转字符串 例如  wonium ->muinow
     *
     * @param charset 需反转的字符串
     * @return 字符串
     */
    public  String reverseString(String charset) {
        charset = isEmpty(charset);
        char[] chars = charset.toCharArray();
        StringBuilder buffer = new StringBuilder();
        for (int i = chars.length - 1; i >= 0; i--) {
            buffer.append(chars[i]);
        }
        return buffer.toString();
    }


    /**
     * 格式化整数 小于10的整数前面补零，大于等于10的整数不变。
     * @param value 整数
     * @return 字符型数字
     */
    public  String formatInt(int value) {
        if (value < 10) {
            return "0" + value;
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * 更改字符集
     * @param str 字符串
     * @param newCharset 如 utf-8
     * @return 字符转码成功返回新的字符串，否则返回null
     * @throws UnsupportedEncodingException
     */
    public String changeCharSet(String str, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

}
