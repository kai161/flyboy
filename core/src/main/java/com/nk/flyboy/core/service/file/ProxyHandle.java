package com.nk.flyboy.core.service.file;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created on 2017/5/15.
 */
public class ProxyHandle implements InvocationHandler {

    public Object proxied;
    public ProxyHandle(Object proxied){
        this.proxied=proxied;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(proxied,args);
    }
}
