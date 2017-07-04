package com.nk.flyboy.core.module.config;

import com.nk.flyboy.core.module.SpringApplicationContextUtil;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created on 2017/7/3.
 */
public class WatchRemoteConfig implements Watcher{

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getType()==Event.EventType.NodeDataChanged){
            ((ConfigurableApplicationContext)SpringApplicationContextUtil.webApplicationContext).refresh();
        }
    }
}
