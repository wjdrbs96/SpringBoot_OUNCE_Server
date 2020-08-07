package me.gyun.ounce.utils;

import java.security.MessageDigest;

public class SHA512EncryptUtils {
    /*
     * 단방향(일방향) 암호화 알고리즘
     * 평문 -> 암호문
     * 암호분 -> 평문 불가능
     * @param planText
     * @return
     */

    public static String encrypt(String planText) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(planText.getBytes());
            byte[] byteData = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
