package com.nk.flyboy.core.module.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created on 2017/7/3.
 */
public class WatchRemoteConfig implements ApplicationContextAware ,Watcher{


    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getType()==Event.EventType.NodeDataChanged){
            ((ConfigurableApplicationContext)this.applicationContext).refresh();
        }
    }
}
