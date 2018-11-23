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

package com.wonium.extension.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
/**
 * @ClassName: WGridView
 * @Description: 重写GridView 的高度，用于解决与ListView 等滑动冲突的问题。
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/23 18:07
 * @UpdateUser: 添加更新者
 * @UpdateDate:  2018/11/23 18:07
 * @UpdateDescription: 更新描述
 * @Version:
 */
public class WGridView extends GridView {
    public WGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGridView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
