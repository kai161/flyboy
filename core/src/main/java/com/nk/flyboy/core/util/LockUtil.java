package com.nk.flyboy.core.util;

/**
 * Created on 2017/10/12.
 */
public class LockUtil {

    private Runnable target;

    private Runnable exceptionHandle;

    public LockUtil(Runnable runnable){
        this.target=runnable;
    }

    public LockUtil(Runnable runnable,Runnable exceptionHandle){
        this.target=runnable;
        this.exceptionHandle=exceptionHandle;
    }

    public String start(){
        try {

            System.out.println("get lock, begin do something");

            target.run();

        }catch (Exception e){
            e.printStackTrace();
            if(exceptionHandle!=null){
                exceptionHandle.run();
            }
        }

        System.out.println("work over, release lock ");

        return "success";
    }
}
