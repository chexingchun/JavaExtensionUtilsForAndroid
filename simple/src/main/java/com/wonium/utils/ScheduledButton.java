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

package com.wonium.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.wonium.example.R;
import com.wonium.extension.utils.ThreadPoolUtil;

import java.util.concurrent.TimeUnit;


/**
 * @ClassName: TimeButton
 * @Description: 发送验证码按钮
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/12/13 10:36
 * @UpdateUser: 添加更新者
 * @UpdateDate: 2018/12/13 10:36
 * @UpdateDescription: 更新描述
 * @Version:
 */
public class ScheduledButton extends AppCompatButton {
    private int scheduledTime = 60;
    private int initialDelay=0;
    private int  period;
    private Drawable srcBackground;
    private String text;
    public ScheduledButton(Context context) {
        this(context, null);
    }

    public ScheduledButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduledButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array =context.obtainStyledAttributes(attrs, R.styleable.ScheduledButton);
        int DEFAULT_TIME = 60;
        scheduledTime=array.getInt(R.styleable.ScheduledButton_scheduled_time, DEFAULT_TIME);
        initialDelay =array.getInt(R.styleable.ScheduledButton_scheduled_initial_delay,0);
        period=array.getInt(R.styleable.ScheduledButton_scheduled_period,1);
        srcBackground =getBackground();
        text=getText().toString();
        array.recycle();
    }

    @SuppressLint ("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    setEnabled(false);
                    if (scheduledTime == 0) {
                        cancel();
                        setEnabled(true);
                        setBackgroundColor(getResources().getColor(com.wonium.extension.R.color.cadetBlue));
                        setText(text);
                    }
                    setText(String.valueOf(scheduledTime-- + "s"));
                    setBackgroundDrawable(srcBackground);
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 开始启动一个定时器
     */
    public void start() {
        ThreadPoolUtil.INSTANCE.schedule(() -> handler.sendEmptyMessage(0), initialDelay, period, TimeUnit.SECONDS);
    }

    /**
     * 取消定时器
     */
    public void cancel() {
        setEnabled(true);
        setText(text);
        setBackgroundDrawable(srcBackground);
        ThreadPoolUtil.INSTANCE.cancel();
    }

}
