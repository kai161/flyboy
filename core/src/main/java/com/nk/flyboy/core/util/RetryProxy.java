package com.nk.flyboy.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created on 2017/9/28.
 */
public abstract class RetryProxy {

    Logger logger= LoggerFactory.getLogger(RetryProxy.class);

    private int retryTimes;

    private int sleepTimes;


    public RetryProxy setRetryTimes(int times) {
        this.retryTimes = times;
        return this;
    }


    public RetryProxy setSleepTimes(int sleepTimes) {
        this.sleepTimes = sleepTimes;
        return this;
    }


    public abstract Object run();

    public Object execute() throws InterruptedException {
        for (int i=0;i<retryTimes;i++){
            try {
               return run();
            }catch (Exception ex){
                logger.error("invoke exception :"+ex);
                Thread.sleep(sleepTimes);
            }
        }
        return null;
    }
}
