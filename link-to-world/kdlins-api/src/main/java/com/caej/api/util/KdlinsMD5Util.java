package com.caej.api.util;

import java.security.MessageDigest;

/**
 * @author miller
 */
public class KdlinsMD5Util {
    private static final char[] bcdLookup = {'0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String token = "jaksldjn&&*^++_&oi3jrddeg";

    /**
     * 将字符串进行MD5加密
     *
     * @param src 源字符串
     * @return 加密后的字符�?
     */
    public static String encrypt(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5", "SUN");
            md5.update(src.getBytes());
            byte[] result = md5.digest();
            return bytesToHexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(byte[] src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5", "SUN");
            md5.update(src);
            byte[] result = md5.digest();
            return bytesToHexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取数字指纹
     *
     * @param src
     * @return
     */
    public static String encryptByUtf8(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5", "SUN");
            md5.update(src.getBytes("UTF-8"));
            byte[] result = md5.digest();
            return bytesToHexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 合作合并获取校验�?
     *
     * @param agentCode
     * @param xmlString
     * @return
     */
    public static String getCheckNo(String agentCode, String xmlString) {
        return encryptByUtf8(token + xmlString + agentCode);
    }

    /**
     * 将加密字符数组转成十六进制字符串
     *
     * @param bcd 加密字符数组
     * @return �?终字符串密文
     */
    private static final String bytesToHexStr(byte[] bcd) {
        StringBuffer s = new StringBuffer(bcd.length * 2);
        for (int i = 0; i < bcd.length; i++) {
            s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
            s.append(bcdLookup[bcd[i] & 0x0f]);
        }
        return s.toString();
    }
}
