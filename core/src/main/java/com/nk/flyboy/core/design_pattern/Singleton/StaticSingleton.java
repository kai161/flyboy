package com.nk.flyboy.core.design_pattern.singleton;

/**
 * Created on 2017/3/22.
 */
public class StaticSingleton {

    private static class SingletonHolder{
        private static final StaticSingleton STATIC_SINGLETON=new StaticSingleton();
    }

    private StaticSingleton(){}

    public static StaticSingleton getStaticSingleton(){
        return SingletonHolder.STATIC_SINGLETON;
    }

    public void say(){
        System.out.println("hello StaticSingleton");
    }
}
