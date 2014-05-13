package com.mk.security.security.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2014/5/13.
 */
public class MD5Encoder {
    public static String encode(String pwd) {
        try {
            //拿到MD5加密的对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //返回一个加密后的字节数组
            byte[] bytes = messageDigest.digest(pwd.getBytes());
            StringBuffer sb = new StringBuffer();
            String tmp;
            for (int i = 0; i < bytes.length; i++) {
                //把字节转换为16进制的字符串
                tmp = Integer.toHexString(0xff & bytes[i]);
                if (tmp.length() == 1) {
                    sb.append("0" + tmp);
                } else {
                    sb.append(tmp);
                }
            }
            return  sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("没有这个加密算法" + e);
        }
    }
}
