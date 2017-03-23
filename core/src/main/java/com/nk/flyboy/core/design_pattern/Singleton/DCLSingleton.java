package com.nk.flyboy.core.design_pattern.singleton;

/**
 * Created on 2017/3/22.
 */
public class DCLSingleton {
    private DCLSingleton(){}
    private static volatile DCLSingleton dclSingleton;
    public static DCLSingleton getDclSingleton(){
        if(dclSingleton==null){
            synchronized (DCLSingleton.class){
                if(dclSingleton==null){
                    dclSingleton=new DCLSingleton();
                }
            }
        }
        return dclSingleton;
    }

    public void say(){
        System.out.println("hello DCLSingleton");
    }
}
