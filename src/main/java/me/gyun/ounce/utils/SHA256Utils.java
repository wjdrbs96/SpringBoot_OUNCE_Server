package me.gyun.ounce.utils;

import java.security.MessageDigest;

public class SHA256Utils {
    public static String encrypt(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = text.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; i++)
                sb.append(Integer.toHexString(0xff & digested[i]));
            return sb.toString();
        } catch (Exception e) {
            return text;
        }
    }
}
