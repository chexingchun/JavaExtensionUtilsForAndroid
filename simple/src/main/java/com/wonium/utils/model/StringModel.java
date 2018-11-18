/*
 * Copyright  2018  WoNium,Joy,Lokiwife,JohnDwang
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

package com.wonium.utils.model;

/**
 * @ClassName: StringModel.java
 * @Description: StringActivity的Model层
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/17 18:08
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/17 18:08
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public interface StringModel extends BaseModel {
    /**
     * 判断字符串是否为空 true 表示为空 false 不为空
     *
     * @param character
     * @param callBack
     */
    void isNull(String character, CallBack<String, String> callBack);

    /**
     * 判空 ，如果是空则返回 “” ，否则返回character
     *
     * @param character 需要判断的字符串
     * @param callBack  结果返回监听器
     */
    void isEmpty(String character, CallBack<String, String> callBack);

    /**
     * 将任意类型转换成String
     * @param t 泛型对象
     * @param callBack 结果返回监听器
     * @param <T> 参数类型
     */
    <T> void valueOf(T t, CallBack<String, String> callBack);

    /**
     *  从map中获取指定Key 对应的Value 值，并将其转换成String 返回，如果找不到key 对应的值则返回默认值
     * @param key 键
     * @param callBack 事件返回监听
     */
    void getStringFromMap(String key, String defaultValue,CallBack<String, String> callBack);
}
