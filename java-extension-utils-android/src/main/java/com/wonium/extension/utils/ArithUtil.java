/*
 * Copyright  2018,  Ethan,Joy,Lokiwife,JohnDwang
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

import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * @ClassName: ArithUtil
 * @Description: 数值计算工具类
 * @Author: Ethan
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/12/11 23:01
 * @UpdateUser: update user
 * @UpdateDate: 2018/12/11 23:01
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */

public enum ArithUtil {

    /**
     * 实例对象
     */
    INSTANCE;


    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public  double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public  double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public  double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后 10 位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public  double div(double v1, double v2) {
        // 默认除法运算精度
        final int defDivScale = 10;
        return div(v1, v2, defDivScale);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由 scale 参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public  double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public  double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");

        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public  String keepTwo(double number) {
        DecimalFormat df = new DecimalFormat("#.000000");
        return df.format(number);
    }

    public  String keepTwo2(double number) {
        DecimalFormat df = new DecimalFormat("#.0");
        return df.format(number);

    }

    /**
     *对于具有零或负范围值，生成得到的字符串，好像将该值转换为具有零刻度一个数值上等于值仿佛零范围值的所有尾部零都出现在结果中。
     *
     * ' - '整个字符串是由一个减号字符的前缀(' u002D')如果非标度值小于零。如果非标度值为零或正数无符号字符作为前缀。
     *
     * 如果此方法的结果传递给构造函数的字符串，此BigDecimal的只有数值一定会被收回;新的BigDecimal表示可以具有不同的精度。
     *
     * 尤其是，如果此BigDecimal具有负的精度，从该方法中得到的字符串将有刻度的零点时由字符串构造处理。
     * @param number 被转换数值
     * @return 返回此BigDecimal的字符串表示形式不带指数字段。为具有正的精度值，中位数的小数点右边的数字用于指示精度
     */
    public  String toPlainString(double number) {
        BigDecimal db = new BigDecimal(number);
        return db.toPlainString();
    }

}
