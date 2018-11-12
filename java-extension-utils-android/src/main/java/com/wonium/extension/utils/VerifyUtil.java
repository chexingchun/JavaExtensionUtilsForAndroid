package com.wonium.extension.utils;

import java.util.regex.Pattern;


/**
 * @ClassName: VerifyUtil.java
 * @Description: 验证类
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/11 18:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/11 18:46
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */

public enum VerifyUtil {
    /**
     * 实例对象
     */
    INSTANCE;

    /**
     * 验证密码
     *
     * @param password
     * @return 以字母开头，长度在6-18之间，只能包含字符、数字和下划线
     */
    public static boolean checkPasswrd(String password) {
        return Pattern.matches("^[a-zA-Z]\\w{5,19}$", password);
    }

    /**
     * 验证手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        return Pattern.matches("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", mobiles);
    }


    /**
     * 手机号码，中间4位星号替换
     *
     * @param phone 手机号
     * @return
     */
    public static String phoneNoHide(String phone) {
        // 括号表示组，被替换的部分$n表示第n组的内容
        // 正则表达式中，替换字符串，括号的意思是分组，在replace()方法中，
        // 参数二中可以使用$n(n为数字)来依次引用模式串中用括号定义的字串。
        // "(\d{3})\d{4}(\d{4})", "$1****$2"的这个意思就是用括号，
        // 分为(前3个数字)中间4个数字(最后4个数字)替换为(第一组数值，保持不变$1)(中间为*)(第二组数值，保持不变$2)
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }

    /**
     * 验证短信
     *
     * @param smsCode
     * @return
     */
    public static boolean isSmsCode(String smsCode) {
        return Pattern.matches("\\d{6}", smsCode);
    }

}
