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

package com.wonium.utils.model.impl;

import com.wonium.extension.utils.StringUtil;
import com.wonium.utils.model.StringModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: StringModelImpl.java
 * @Description: StringModel的实现类
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/17 21:54
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/17 21:54
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public class StringModelImpl implements StringModel {

    @Override
    public void isNull(String character, CallBack<String, String> callBack) {
        if (StringUtil.INSTANCE.isNull(character)) {
            callBack.onSuccess("isNull()  示例：\nStringUtil.INSTANCE.isNull(" + character + ")->true;");
        } else {
            callBack.onFailure("isNull()  示例：\nStringUtil.INSTANCE.isNull(" + character + ")->false;");
        }
    }

    @Override
    public void isEmpty(String character, CallBack<String, String> callBack) {
        callBack.onSuccess("isEmpty() 示例\n StringUtil.InSTANCE.isEmpty(" + character + ")->" + StringUtil.INSTANCE.isEmpty(character));
    }

    @Override
    public <T> void valueOf(T t, CallBack<String, String> callBack) {
        callBack.onSuccess("valueOf(T) 示例\nString.valueOf(" + t + ")->" + String.valueOf(t));
    }

    @Override
    public void getStringFromMap(String key, String defaultValue,CallBack<String, String> callBack) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("string", "wonium");
        map.put("int", 1);
        map.put("double", 3.1415926f);
        callBack.onSuccess("StringUtil.INSTANCE.getStringFromMap(map, " + "double" + " " + ")" + StringUtil.INSTANCE.getStringFromMap(map, key, defaultValue));

    }
}
