package com.nk.flyboy.core.service.zookeeper.configchangetest;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by cheris on 2016/9/5.
 */
public class ConnectionWatch implements Watcher {


    private static final int SESSION_TIMEOUT=5000;

    protected ZooKeeper zooKeeper;
    CountDownLatch countDownLatch=new CountDownLatch(1);

    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState()== Event.KeeperState.SyncConnected){
            countDownLatch.countDown();
        }
    }


    public void connect(String host) throws IOException,InterruptedException{
        zooKeeper=new ZooKeeper(host,SESSION_TIMEOUT,this);
        countDownLatch.await();
    }

    public void close()throws InterruptedException{
        zooKeeper.close();
    }
}
