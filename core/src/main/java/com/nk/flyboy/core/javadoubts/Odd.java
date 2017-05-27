package com.nk.flyboy.core.javadoubts;

/**
 * Created on 2017/5/26.
 *
 * 注意正负数判断
 */
public class Odd {
    public static void main(String[] args) {
        //error
        System.out.println(isOdd(-3));

        System.out.println(isOdd1(-3));
        System.out.println(isOdd2(-3));

    }

    //error
    public static boolean isOdd(int t){
        return t%2==1;
    }

    //true
    public static boolean isOdd1(int t){
        return t%2!=0;
    }

    public static boolean isOdd2(int t){
        return (t&1)!=0;
    }
}
