package com.nk.flyboy.core.module.zookeeper;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created on 2017/7/10.
 */
public class ZookeeperLock {

    private static final int DEFAULT_SESSION_TIMEOUT=25000;

    private static String root="/distribute";

    private ZooKeeper zooKeeper;

    public ZookeeperLock(String url){
        this.zooKeeper=ZookeeperUtils.getZooKeeper(url);
    }

    public void lock(){

    }
}
