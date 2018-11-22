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

package com.wonium.extension.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: JsonUtils
 * @Description: GsonUtils工具类
 * @Author: JohnDwang
 * @E-mail: whd_tech_vip@163.com
 * @Blog: https://blog.csdn.net/WHD472099458
 * @CreateDate: 2018/11/22 21:41
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/22 21:41
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public enum GsonUtil {

    /**
     * 实例对象
     */
    INSTANCE;
        private static Gson gson = null;
        static {
            if (gson == null) {
                gson = new Gson();
            }
        }

        private GsonUtil() {
        }

        /**
         * 转成json
         *
         * @param object
         * @return
         */
        public String GsonString(Object object) {
            String gsonString = null;
            if (gson != null) {
                gsonString = gson.toJson(object);
            }
            return gsonString;
        }

        /**
         * 转成bean
         *
         * @param gsonString
         * @param cls
         * @return
         */
        public  <T> T GsonToBean(String gsonString, Class<T> cls) {
            T t = null;
            if (gson != null) {
                t = gson.fromJson(gsonString, cls);
            }
            return t;
        }

        /**
         * 转成list
         *
         * @param gsonString
         * @param cls
         * @return
         */
        public  <T> List<T> GsonToList(String gsonString, Class<T> cls) {
            List<T> list = null;
            if (gson != null) {
                list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
                }.getType());
            }
            return list;
        }

        /**
         * 转成list中有map的
         *
         * @param gsonString
         * @return
         */
        public  <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
            List<Map<String, T>> list = null;
            if (gson != null) {
                list = gson.fromJson(gsonString,
                        new TypeToken<List<Map<String, T>>>() {
                        }.getType());
            }
            return list;
        }

        /**
         * 转成map的
         *
         * @param gsonString
         * @return
         */
        public  <T> Map<String, T> GsonToMaps(String gsonString) {
            Map<String, T> map = null;
            if (gson != null) {
                map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
                }.getType());
            }
            return map;
        }
}
