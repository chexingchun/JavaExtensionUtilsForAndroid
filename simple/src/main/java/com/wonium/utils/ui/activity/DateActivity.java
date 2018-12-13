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

package com.wonium.utils.ui.activity;

import android.support.v7.widget.AppCompatButton;

import com.wonium.example.R;
import com.wonium.utils.ScheduledButton;
import com.wonium.extension.utils.ToastUtil;

public class DateActivity extends BaseActivity {
    private ScheduledButton btnTime;
    private AppCompatButton btnStop;
    @Override
    public void initView(int layoutResID) {
        setContentView(layoutResID);
        btnTime =findViewById(R.id.btn_time);
        btnStop =findViewById(R.id.btn_stop);
    }

    @Override
    public void initListener() {
        btnTime.setOnClickListener(v -> {btnTime.start();
        ToastUtil.INSTANCE.show(getContext(),"开始");
        });

        btnStop.setOnClickListener(v -> {
            btnTime.cancel();
            ToastUtil.INSTANCE.show(getContext(),"暂停");
        });

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_date;
    }
}
