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

package com.wonium.utils.presenter;

import android.content.Context;

/**
 * @ClassName: MainPresenter.java
 * @Description: MainActivity的Presenter层
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/17 13:43
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/17 13:43
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public interface MainPresenter extends BasePresenter {
    void getListData(Context context);
}
