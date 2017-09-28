package com.nk.flyboy.core.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 2017/9/28.
 */
public class RetryProxy<T> {

    private int times;

    private T proxyObj;

    public RetryProxy(T t) {
        this.proxyObj = t;
    }

    public T retryProxy(int retryTimes,String... methods) {
        times = retryTimes;
        return (T) Proxy.newProxyInstance(proxyObj.getClass().getClassLoader(), new Class[]{proxyObj.getClass()}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if(Arrays.asList(methods).contains(method.getName())){
                    System.out.println(times);
                    Object obj = null;
                    while (times < 0) {
                        obj = method.invoke(proxyObj, args);
                        if (obj != null) break;
                        times--;
                        Thread.sleep(1000);
                        System.out.println("this is " + times);
                    }
                    return obj;
                }else {
                    return method.invoke(proxyObj,args);
                }

            }
        });
    }

}
