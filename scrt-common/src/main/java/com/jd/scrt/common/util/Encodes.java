package com.jd.scrt.common.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 封装各种格式的编码解码工具类.
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class Encodes {

    private static final String DEFAULT_URL_ENCODING = "UTF-8";
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * Hex编码.
     */
    public static String encodeHex(byte[] input) throws Exception {
        return Hex.encodeHexString(input);
    }

    /**
     * Hex解码.
     */
    public static byte[] decodeHex(String input) throws Exception {
        return Hex.decodeHex(input.toCharArray());
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(byte[] input) throws Exception {
        return Base64.encodeBase64String(input);
    }

    /**
     * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
     */
    public static String encodeUrlSafeBase64(byte[] input) throws Exception {
        return Base64.encodeBase64URLSafeString(input);
    }

    /**
     * Base64解码.
     */
    public static byte[] decodeBase64(String input) throws Exception {
        return Base64.decodeBase64(input);
    }

    /**
     * Base62编码。
     */
    public static String encodeBase62(byte[] input) throws Exception {
        char[] chars = new char[input.length];
        for (int i = 0; i < input.length; i++) {
            chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
        }
        return new String(chars);
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     */
    public static String encodeUrl(String part) throws Exception {
        return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     */
    public static String decodeUrl(String part) throws Exception {
        return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
    }

    /**
     * MD5加密
     *
     * @param code 原始字符串
     * @return 返回字符串的MD5码
     */
    public static String md5(String code) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bytes = code.getBytes();
        byte[] results = messageDigest.digest(bytes);
        StringBuilder stringBuilder = new StringBuilder();
        for (byte result : results) {
            // 将byte数组转化为16进制字符存入stringbuilder中
            stringBuilder.append(String.format("%02x", result));
        }
        return stringBuilder.toString();
    }
}
