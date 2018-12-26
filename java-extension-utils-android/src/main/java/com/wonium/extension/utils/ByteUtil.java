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

import com.google.common.primitives.Ints;
import com.wonium.extension.Endian;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * @ClassName: ByteUtil.java
 * @Description: 字节处理工具类
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/12 21:02
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/12 21:02
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public enum ByteUtil {

    /**
     * 实例对象
     */
    INSTANCE;

    /**
     * 获取index位置的bit值
     *
     * @param value 字节数据
     * @param index 字节数据的bit位索引  index的范围为0-7
     * @return bit位的值 结果范围为0，1  例如 240 获取bit7位置的bit值  返回1
     */

    public byte getBitValue(byte value, int index) {
        return (byte) ((value >> index) & 0x1);
    }


    /**
     * @param result 返回结果
     * @param bitValue  bitn-bitm位
     * @param offset 下标偏移位置
     * @return byte
     */
    private byte setBitValue(byte result, byte bitValue, int offset) {
        bitValue <<= offset;
        result = (byte) (bitValue | result);
        return result;
    }


    /**
     * 取一个字节的start 到end 位 转换成十进制数
     *
     * @param src   一个字节数据 8bit
     * @param start bit起始位
     * @param end   bit 截止位
     * @return int 起始位到截止位的bit数 转换成十进制的值
     */
    public int getSubBitValue(byte src, int start, int end) {
        int value = 0;
        for (int i = end; i >= start; i--) {
            int x = (src >> i) & 0x1;
            System.out.print(x);
            value += x * Math.pow(2, i - start);
        }
        return value;
    }

    /**
     * 将一个字节数组转成short,endian判断字节序，如果endian 为Endian.BIG 则为大端在前，如果endian为Endian.LITTLE 则为小端在前
     *
     * @param bytes 字节数组
     * @return short 一个short类型的整数
     */
    public short byteArrayToShort(byte[] bytes, Endian endian) {
        int maxLength = 2;
        if (bytes.length != maxLength) {
            throw new IndexOutOfBoundsException("长度超过最大值");
        }
        if (endian == Endian.BIG) {
            return (short) ((bytes[0] << 8) | bytes[1] & 0xFF);
        } else {
            return (short) (((bytes[1] & 0xff) << 8) | ((bytes[0] & 0xff)));
        }
    }

    /**
     * short 转字节数组
     *
     * @param value short类型的证书
     * @return
     */
    public byte[] shortToByteArray(short value, Endian endian) {
        byte[] bytes = new byte[2];
        if (endian == Endian.BIG) {
            bytes[0] = (byte) (value >> 8 & 0xFF);
            bytes[1] = (byte) (value & 0xFF);
        } else {
            bytes[0] = (byte) (value & 0xFF);
            bytes[1] = (byte) (value >> 8 & 0xFF);
        }

        return bytes;
    }

    /**
     * 字节数组转换成Int 低字节在前，高字节在后
     *
     * @param bytes  被转换得bytes数组
     * @param endian 字节序 大端小端
     * @return int
     */
    public int byteArrayToInt(byte[] bytes, Endian endian) {
        int maxLength = 4;
        if (bytes.length != maxLength) {
            throw new IndexOutOfBoundsException("the bytes index out of bounds");
        }
        if (endian == Endian.BIG) {
            return (((bytes[0] & 0xff) << 24) | ((bytes[1] & 0xff) << 16) | ((bytes[2] & 0xff) << 8) | ((bytes[3] & 0xff)));
        } else {
            return (((bytes[3] & 0xff) << 24) | ((bytes[2] & 0xff) << 16) | ((bytes[1] & 0xff) << 8) | ((bytes[0] & 0xff)));
        }
    }


    /**
     * int 整数转换成字节数组
     *
     * @param value  需转换的int 值
     * @param endian 字节序 大端小端
     * @return bytes byte[]
     */
    public byte[] intToByteArray(int value, Endian endian) {
        byte[] bytes = new byte[4];
        if (endian == Endian.BIG) {
            bytes[0] = (byte) ((value >> 24) & 0xFF);
            bytes[1] = (byte) ((value >> 16) & 0xFF);
            bytes[2] = (byte) ((value >> 8) & 0xFF);
            bytes[3] = (byte) (value & 0xFF);
        } else {
            bytes[3] = (byte) ((value >> 24) & 0xFF);
            bytes[2] = (byte) ((value >> 16) & 0xFF);
            bytes[1] = (byte) ((value >> 8) & 0xFF);
            bytes[0] = (byte) (value & 0xFF);
        }
        return bytes;
    }

    /**
     * 字节数组转层Float
     *
     * @param bytes  字节数组
     * @param endian 字节序 大端小端
     * @return value float
     */
    public float byteArrayToFloat(byte[] bytes, Endian endian) {
        int maxLength = 4;
        if (bytes.length != maxLength) {
            throw new IndexOutOfBoundsException("the byte array b index out of bounds ");
        }
        int value;
        if (endian == Endian.BIG) {
            value = (((bytes[0] & 0xff) << 24) | ((bytes[1] & 0xff) << 16) | ((bytes[2] & 0xff) << 8) | ((bytes[3] & 0xff)));
        } else {
            value = (((bytes[3] & 0xff) << 24) | ((bytes[2] & 0xff) << 16) | ((bytes[1] & 0xff) << 8) | ((bytes[0] & 0xff)));
        }

        return Float.intBitsToFloat(value);
    }

    /**
     * float 转ByteArray
     * @param value float类型数值
     * @param endian 字节序大小端
     * @return
     */

    public byte[] floatToByteArray(float value,Endian endian){
        byte[] bytes =new byte[4];
        int fl=Float.floatToIntBits(value);
        if (endian == Endian.BIG) {
            bytes[0] = (byte) ((fl >> 24) & 0xFF);
            bytes[1] = (byte) ((fl >> 16) & 0xFF);
            bytes[2] = (byte) ((fl >> 8) & 0xFF);
            bytes[3] = (byte) (fl & 0xFF);
        } else {
            bytes[3] = (byte) ((fl >> 24) & 0xFF);
            bytes[2] = (byte) ((fl >> 16) & 0xFF);
            bytes[1] = (byte) ((fl >> 8) & 0xFF);
            bytes[0] = (byte) (fl & 0xFF);
        }
        return bytes;
    }

    /**
     *
     * @param bytes
     * @param endian
     */
    public long byteArrayToLong(byte[] bytes, Endian endian) {
        if (bytes.length != 8) {
            throw new IndexOutOfBoundsException("the byte array b index out of bounds ");
        }

        if (endian == Endian.BIG) {
            return ((((long) bytes[0] & 0xff) << 56) | (((long) bytes[1] & 0xff) << 48) | (((long) bytes[2] & 0xff) << 40) | (((long) bytes[3] & 0xff) << 32) | ((
                    (long) bytes[4] & 0xff) << 24) | (((long) bytes[5] & 0xff) << 16) | (((long) bytes[6] & 0xff) << 8) | (((long) bytes[7] & 0xff)));
        } else {

            return ((((long) bytes[7] & 0xff) << 56) | (((long) bytes[6] & 0xff) << 48) | (((long) bytes[5] & 0xff) << 40) | (((long) bytes[4] & 0xff) << 32) | ((
                    (long) bytes[3] & 0xff) << 24) | (((long) bytes[2] & 0xff) << 16) | (((long) bytes[1] & 0xff) << 8) | (((long) bytes[0] & 0xff)));
        }
    }

    /**
     * long 转字节数组
     * @param value 一个long 类型的数值
     * @param endian 字节序 大端小端
     * @return byte[] 字节数组
     */
    public byte[] longToByteArray(long value, Endian endian) {
        byte[] bytes = new byte[8];
        if (endian == Endian.BIG) {
            bytes[0] = (byte) (value >> 56);
            bytes[1] = (byte) (value >> 48);
            bytes[2] = (byte) (value >> 40);
            bytes[3] = (byte) (value >> 32);
            bytes[4] = (byte) (value >> 24);
            bytes[5] = (byte) (value >> 16);
            bytes[6] = (byte) (value >> 8);
            bytes[7] = (byte) (value);
        } else {
            bytes[7] = (byte) (value >> 56);
            bytes[6] = (byte) (value >> 48);
            bytes[5] = (byte) (value >> 40);
            bytes[4] = (byte) (value >> 32);
            bytes[3] = (byte) (value >> 24);
            bytes[2] = (byte) (value >> 16);
            bytes[1] = (byte) (value >> 8);
            bytes[0] = (byte) (value);
        }
        return bytes;
    }

    /**
     * 把byte转为字符串的bit
     *
     * @param b 需要处理的字节
     * @return string
     */
    public String byteToBit(byte b) {
        return String.valueOf((byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) (b & 0x1));
    }

    /**
     * int 数组转换为list
     *
     * @param arrays 整型数组
     * @return List 集合
     */
    public List<Integer> intArrayToList(int[] arrays) {
        return  Ints.asList(arrays);
    }

    /**
     * 二进制打印字节数组
     *
     * @param bytes 字节数组
     * @return builder 二进制数据字符串
     */
    public String printByteArrayToBinary(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        if (bytes == null) {
            throw new NullPointerException("bytes is null");
        }
        for (byte b : bytes) {
            builder.append(byteToBit(b));
        }
        return builder.toString();
    }

    /**
     * 十六进制打印字节数组，每打印16个字节换行
     *
     * @param data 需打印的字节数组
     * @return String 转换成16进制后的字符串
     */
    public String printHexBinary(byte[] data) {
        char[] hexCode = "0123456789ABCDEF".toCharArray();
        StringBuilder r = new StringBuilder(data.length * 2);
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
            r.append("  ");

            if ((i + 1) % 16 == 0) {
                r.append("\n");
            }
        }
        return r.toString();
    }

    /**
     * 字节数组追加操作
     *
     * @param total 被追加的字节数组
     * @param data  追加的字节数组
     * @return byte[] 追加后的字节数组
     */
    public byte[] append(byte[] total, byte[] data) {
        if (total == null) {
            total = new byte[0];
        }
        ByteBuffer buffer = ByteBuffer.allocate(total.length + data.length);
        buffer.put(total);
        buffer.put(data);

        return buffer.array();
    }

    /**
     *
     * @param data 字节数组
     * @return string 字节数组专成String
     */
    public String byteArrayToText(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append((char) b);
        }
        return sb.toString();
    }

    /**
     * 从起始位置到index 位置 截取字符数组
     *
     * @param src   原byte[] 数组
     * @param index 截取的长度
     * @return 新的byte[]
     */
    public byte[] getSubByteArray(byte[] src, int index) {
        byte[] subByteArray = new byte[index];
        System.arraycopy(src, 0, subByteArray, 0, subByteArray.length);
        return subByteArray;
    }
}
