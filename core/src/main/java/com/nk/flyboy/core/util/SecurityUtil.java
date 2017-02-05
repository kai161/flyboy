package com.nk.flyboy.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kai on 2017/2/5.
 */
public class SecurityUtil {

    /**
     * 简单MD5加密
     * @param strToHash
     * @return
     */
    public static String MD5(String strToHash){

        String md5=null;

        try {
            MessageDigest md= MessageDigest.getInstance("MD5");

            byte[] bytes= md.digest(strToHash.getBytes());

            StringBuilder stringBuilder=new StringBuilder(32);

            for(int i=0;i<bytes.length;i++){
                stringBuilder.append(Integer.toString(((bytes[i]&0xff))+0x100,16).substring(1));
            }

            md5=stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return md5;
    }

    public static void main(String[] args) {

    }
}
