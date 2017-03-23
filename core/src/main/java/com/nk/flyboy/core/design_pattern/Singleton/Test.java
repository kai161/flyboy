package com.nk.flyboy.core.design_pattern.singleton;

/**
 * Created on 2017/3/22.
 */
public class Test {

    public static void main(String[] args) {
        DCLSingleton dclSingleton=DCLSingleton.getDclSingleton();
        dclSingleton.say();

        StaticSingleton staticSingleton=StaticSingleton.getStaticSingleton();
        staticSingleton.say();
    }
}
