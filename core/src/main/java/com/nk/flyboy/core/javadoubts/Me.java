package com.nk.flyboy.core.javadoubts;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Created on 2017/5/27.
 *
 *
 */
public class Me {

    public static void main(String[] args) {

     /*   //error  replaceAll 方法第一个参数接受的的是正则表达式，"." 被当做了正则表达式 匹配所有的字符
        System.out.println(Me.class.getName().replaceAll(".","/")+".class");
        System.out.println("test.test1.test2".replaceAll(".","/")+".class");

        //true
        System.out.println(Me.class.getName().replaceAll("\\.","/")+".class");
        System.out.println("test.test1.test2".replaceAll(Pattern.quote("."),"/")+"class");

        System.out.println(Me.class.getName().replace(".","/")+".class");*/

        BigDecimal bigDecimal=new BigDecimal("2324.123121");
        System.out.println(bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).toString());

    }
}
