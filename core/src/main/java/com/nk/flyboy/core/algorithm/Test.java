package com.nk.flyboy.core.algorithm;

/**
 * Created on 2017/11/13.
 */
public class Test {

    public static void main(String[] args) {
        A a=new A(1,2);

        change(a);

        System.out.println(a.a);
        System.out.println(a.b);
    }


    public static class A{
        private int a;
        private int b;

        public A(int a,int b){
            this.a=a;
            this.b=b;
        }


    }

    public static void change(A a){
        a.a=4;
        a.b=5;
    }
}
