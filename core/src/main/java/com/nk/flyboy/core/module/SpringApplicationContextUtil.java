package com.nk.flyboy.core.module;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/7/4.
 */
@Component
public class SpringApplicationContextUtil implements ApplicationContextAware {

    public static ApplicationContext webApplicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        webApplicationContext=applicationContext;
    }
}
