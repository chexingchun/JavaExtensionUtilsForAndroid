package com.wonium.extension.utils;

import android.graphics.Color;

import com.google.common.primitives.Ints;

import java.util.List;


public enum  ColorUtil {
    INSTANCE;

    private   int[] TEXT_COLOR_LIST = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.rgb(255, 0, 255), Color.CYAN};


    public  int intToColor(int co) {
        int red = (co & 0xff0000) >> 16;
        int green = (co & 0x00ff00) >> 8;
        int blue = (co & 0x0000ff);
        return Color.rgb(red, green, blue);
    }

    /**
     * int 数组转换为list
     * @param colors
     * @return
     */
    public   List<Integer>  colorArrayToList(int[] colors){
        return Ints.asList(colors);
    }

    public  List<Integer> getSubColors(int start,int length){
        int[] subColor=new int[length];
        System.arraycopy(ColorUtil.INSTANCE.TEXT_COLOR_LIST,start,subColor,0,length);
        return colorArrayToList(subColor);
    }


    /**
     * 生成两个颜色之间的过渡颜色
     * @param currColor 起始颜色
     * @param targetColor 结束颜色
     * @return
     */
    public  int gradualChangeColor(int currColor, int targetColor) {
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

    public  int cycleColor(int currentColor,int[] arrays) {
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
