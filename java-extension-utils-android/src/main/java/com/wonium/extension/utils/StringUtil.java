package com.wonium.extension.utils;

import android.text.TextUtils;

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

    public String isEmpty(String text) {
        if (text == null || "".equals(text) || text.length() == 0) {
            return "";
        } else {
            return text;
        }
    }

    public boolean isNull(CharSequence text) {
        return TextUtils.isEmpty(text);
    }


    public <T> String valueOf(T value) {
        return String.valueOf(value);
    }

    public String getString(Map map, String key, String defaultValue) {
        Pattern intPattern = Pattern.compile("^[-\\+]?[\\d]*\\.0*$");
        Object obj = map.get(key);
        return obj == null ? defaultValue : (obj instanceof Number && intPattern.matcher(obj.toString()).matches() ? String.valueOf(Long.valueOf(((Number) obj).longValue())) : obj.toString());
    }

}
