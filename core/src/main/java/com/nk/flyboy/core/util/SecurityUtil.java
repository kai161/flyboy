package com.nk.flyboy.core.util;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        BigDecimal b1=new BigDecimal(134);
        BigDecimal b2=new BigDecimal(100);

        BigDecimal b3=b1.remainder(b2);

        BigDecimal b4=b1.subtract(b3);

        System.out.println(b3);
        System.out.println(b4);

        Calendar lockTimeCalendar = Calendar.getInstance();
        lockTimeCalendar.add(Calendar.MINUTE, -30);
        Date lockTime = lockTimeCalendar.getTime();

        System.out.println(lockTime);

        Calendar v = Calendar.getInstance();
        v.set(Calendar.HOUR_OF_DAY, 0);
        v.set(Calendar.MINUTE, 0);
        v.set(Calendar.SECOND, 0);
        v.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");

        System.out.println(sdfDate.format(v.getTime()));
        System.out.println(v.getTime());

        System.out.println();

    }
}
