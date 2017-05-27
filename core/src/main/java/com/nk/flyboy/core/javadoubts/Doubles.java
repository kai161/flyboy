package com.nk.flyboy.core.javadoubts;

import java.math.BigDecimal;

/**
 * Created on 2017/5/26.
 *
 * 计算机不能准确表达double数据，考虑用BigDecimal,或者先转换成整形数据
 *
 *这里要告诫你一点: 一定要用BigDecimal(String)构造器，而千万不要用 BigDecimal(double)。后一个构造后一个构造器将用它的参数的“精确”值来创建一个实例
 */
public class Doubles {

    public static void main(String[] args) {

        //error
        System.out.println(2.00-1.10);


        //true
        System.out.println(new BigDecimal("2.00").subtract(new BigDecimal("1.10")));

        System.out.println((200-110));
    }


}
