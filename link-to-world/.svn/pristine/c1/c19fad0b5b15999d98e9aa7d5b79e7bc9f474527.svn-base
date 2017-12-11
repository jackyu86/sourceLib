package com.caej.api.util;

import java.security.MessageDigest;

/**
 * @author miller
 */
public class MD5Encrypt {
    public static String encrypt(String source) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] digest = messageDigest.digest(source.getBytes("utf-8"));
        int i;
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : digest) {
            i = b;
            if (i < 0) i += 256;
            if (i < 16)
                stringBuilder.append("0");
            stringBuilder.append(Integer.toHexString(i));
        }
        return stringBuilder.toString();
    }
}
