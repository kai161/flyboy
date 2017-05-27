package com.nk.flyboy.core.javadoubts;

/**
 * Created on 2017/5/26.
 */
public class LongDivision {

    public static void main(String[] args) {

        //error  计算时会按照int 进行计算，造成溢出。所以结果不准
        long micros=24*60*60*1000*1000;
        long millis=24*60*60*1000;
        System.out.println(micros/millis);

        //true 一开始就指定是long。不会有溢出
        long micros1=24L*60*60*1000*1000;
        long millis1=24L*60*60*1000;
        System.out.println(micros1/millis1);
    }
}
