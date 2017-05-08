package com.nk.flyboy.core.service.file;

/**
 * Created on 2017/5/8.
 */
public class SuperClass {
    public static final String fianlString="111";
    public static String staticString="222";
    static {
        System.out.println("superclass static load");
    }

    public SuperClass(){
        System.out.println("superclass load");
    }
}
