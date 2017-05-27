package com.nk.flyboy.core.javadoubts;

/**
 * Created on 2017/5/26.
 *
 *第二条语句使用的是简单赋值操作符(=)，而第一条语句使用的是复合赋值操作符。
 * (复合赋值操作符包括 +=、-=、*=、/=、%=、<<=、>>=、>>>=、&=、^=和|=)Java 语言规范中讲到，
 * 复合赋值 E1 op= E2 等价于简单赋值 E1 =(T)((E1)op(E2))，其中 T 是 E1 的类型，除非 E1 只被计算一次。
 */
public class ComplexOption {

    public static void main(String[] args) {
        short x=0;
        int y=123456;
        System.out.println(x+=y);


    }
}
