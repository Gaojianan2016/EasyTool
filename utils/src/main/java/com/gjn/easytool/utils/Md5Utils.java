package com.gjn.easytool.utils;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author gjn
 * @time 2019/4/4 9:56
 */

public class Md5Utils {
    public static String toMD5(String text) {
        try {
            //获取摘要器 MessageDigest
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //通过摘要器对字符串的二进制字节数组进行hash计算
            byte[] digest = messageDigest.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aDigest : digest) {
                //循环每个字符 将计算结果转化为正整数;
                int digestInt = aDigest & 0xff;
                //将10进制转化为较短的16进制
                String hexString = Integer.toHexString(digestInt);
                //转化结果如果是个位数会省略0,因此判断并补0
                if (hexString.length() < 2) {
                    sb.append(0);
                }
                //将循环结果添加到缓冲区
                sb.append(hexString);
            }
            //返回整个结果
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e("Md5Utils", "算法不存在", e);
        }
        return text;
    }
}
