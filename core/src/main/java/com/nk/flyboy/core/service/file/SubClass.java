package com.nk.flyboy.core.service.file;

/**
 * Created on 2017/5/8.
 */
public class SubClass extends SuperClass {
    public static final String subFinalString="333";
    public static String subStaticString="444";

    static {
        System.out.println("subclass static class");
    }

    public SubClass(){
        System.out.println("subclass load");
    }
}
