package com.nk.flyboy.core.javadoubts;

/**
 * Created on 2017/5/26.
 *
 *使用字符串连接操作符使用格外小心。+ 操作符当且仅当它的操作数中至
 *少有一个是 String 类型时，才会执行字符串连接操作;否则，它执行的就是加法。
 */
public class LastLaugh {

    public static void main(String[] args) {


        try {
            throw new NullPointerException();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        //error
        System.out.println("H"+"a");
        System.out.println('H'+'a');


        //true
        System.out.println(""+'H'+'a');
        System.out.println(2+2+"=2+2");
    }
}
