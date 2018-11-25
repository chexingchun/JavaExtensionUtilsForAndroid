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
package com.wonium.utils.ui.view;
/**
 * @ClassName: StringView.java
 * @Description: StringActivity的View层
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/17 22:07
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/17 22:07
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public interface StringView extends BaseView {
        /**
         * 显示isNull message
         * @param message 显示内容
         */
        void showTestIsNullMessage(String message);

        /**
         * 显示isEmptyMessage
         * @param message
         */
        void showTestIsEmptyMessage(String message);
        void showTestValueOfMessage(String message);
        void showTestGetStringFromMap(String message);

}
