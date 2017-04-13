package com.nk.flyboy.core.util;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created on 2017/4/11.
 */
public class IDGenerator {

    private static long sequence=0L;
    private static long lastTimeStamp=-1L;
    private static long machineId=0L;

    static {
        String localIP=MachineUtil.getRealIP();
        //获取服务器所在IP第四段的值最大 255；
        machineId=Long.parseLong(localIP.substring(localIP.lastIndexOf(".")+1));
    }

    /**
     * ID构成：
     * 8bit machineId+41bit timestamp+12bit sequence
     * 思路：
     * 1、获取服务器所在的IP并将IP第四段号作为服务器号 因IPv4 第四段最大255，最大占 8bit
     * 2、获取当前时间戳，毫秒级，最大占用 41bit
     * 3、生成序列号，同一毫秒内最多支持产生 4095个序列号，不同毫秒序列号置0重记 最大占用 12bit
     * @return
     */
    public static long generateId(){

        synchronized (IDGenerator.class){
            //当前时间戳
            long timestamp=System.currentTimeMillis();

            if(timestamp==lastTimeStamp){
                sequence=(sequence+1L)&4095;
                if(sequence==0){
                    for(timestamp=System.currentTimeMillis();timestamp<=lastTimeStamp;timestamp=System.currentTimeMillis()){
                        ;
                    }
                }
            }else {
                sequence=0L;
            }

            if(timestamp<lastTimeStamp){
                return 0;
            }else {
                lastTimeStamp=timestamp;
                return machineId<<53|(timestamp-1412092800000L)<<12|sequence;
            }
        }

    }

    public static void main(String[] args) {

        long id=0;

        long start=System.currentTimeMillis();
        for(int i=0;i<1000000;i++){
            id=generateId();
            System.out.println(id);
        }
        long end=System.currentTimeMillis();

        long runTime=end-start;

        System.out.println(runTime);


//        System.out.println(Long.toBinaryString(1108212679216902148L));
//
//        System.out.println(Integer.valueOf("000000000100",2));
//        System.out.println(Integer.valueOf("1111011",2));
//        System.out.println(Long.valueOf("00001001010011000111101101000010011101011",2));
//
//        System.out.println(new Date(79875704043L+1412092800000L));


//        String[] list={Long.toBinaryString(1108212722468794393L),Long.toBinaryString(1108212722468798464L),Long.toBinaryString(1108212722468802560L)
//                ,Long.toBinaryString(1108212722468806656L)};
//
//        for(String test:list ){
//            System.out.println(Integer.valueOf(test.substring(0,7),2));
//            System.out.println(Long.valueOf(test.substring(8,49),2));
//            System.out.println(Long.valueOf(test.substring(50),2));
//        }


    }
}
