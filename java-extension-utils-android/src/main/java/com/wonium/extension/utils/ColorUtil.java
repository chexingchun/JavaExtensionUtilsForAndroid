/*
 * Copyright  2018  wonium
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

import android.graphics.Color;

/**
 * @ClassName: ColorUtil.java
 * @Description: 颜色工具类
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/12 21:02
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/12 21:02
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public enum ColorUtil {
    /**
     * 实例对象
     */
    INSTANCE;


    public int intToColor(int co) {
        int red = (co & 0xff0000) >> 16;
        int green = (co & 0x00ff00) >> 8;
        int blue = (co & 0x0000ff);
        return Color.rgb(red, green, blue);
    }

    /**
     * 生成两个颜色之间的过渡颜色
     *
     * @param currColor   起始颜色
     * @param targetColor 结束颜色
     * @return
     */
    public int gradualChangeColor(int currColor, int targetColor) {
        int currentColor;
        int currRed = (currColor & 0xff0000) >> 16;
        int currGreen = (currColor & 0x00ff00) >> 8;
        int currBlue = (currColor & 0x0000ff);

        int targetRed = (targetColor & 0xff0000) >> 16;
        int targetGreen = (targetColor & 0x00ff00) >> 8;
        int targetBlue = (targetColor & 0x0000ff);

        if (currRed < targetRed) {
            currRed += 10;
            if (currRed > 255) {
                currRed = 255;
            }
        }
        if (currGreen < targetGreen) {
            currGreen += 10;
            if (currGreen > 255) {
                currGreen = 255;
            }
        }
        if (currBlue < targetBlue) {
            currBlue += 10;
            if (currBlue > 255) {
                currBlue = 255;
            }
        }

        if (currRed > targetRed) {
            currRed -= 10;
            if (currRed < 0) {
                currRed = 0;
            }
        }
        if (currGreen > targetGreen) {
            currGreen -= 10;
            if (currGreen < 0) {
                currGreen = 0;
            }
        }
        if (currBlue > targetBlue) {
            currBlue -= 10;
            if (currBlue < 0) {
                currBlue = 0;
            }
        }

        currentColor = Color.rgb(currRed, currGreen, currBlue);

        return currentColor;
    }

    public int cycleColor(int currentColor, int[] arrays) {
        for (int i = 0; i < arrays.length; i++) {
            if (ColorUtil.INSTANCE.intToColor(currentColor) == ColorUtil.INSTANCE.intToColor(arrays[i])) {
                if (i == arrays.length - 1) {
                    currentColor = arrays[0];
                    break;
                } else {
                    currentColor = arrays[i + 1];
                    break;
                }
            }
        }
        return currentColor;
    }
}
