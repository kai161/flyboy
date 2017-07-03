package com.nk.flyboy.core.service.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Created by cheris on 2016/9/2.
 */
public class TestMain {

    static boolean flag=true;
    public static void main(String[] arg){
        try {

            ZooKeeper zooKeeper=new ZooKeeper("172.16.184.128:2181", 50000, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    if(watchedEvent.getState()== Event.KeeperState.SyncConnected){
                        System.out.println("zookeeper connect ....");
                    }
                }
            });

            Stat stat=zooKeeper.exists("/config/test2", new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("zookeeper exists method ...");
                    flag=false;
                }
            });
            if(stat!=null){
                System.out.println(stat.getVersion());
            }else {
                zooKeeper.create("/config/test2", "testData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }


            byte[] data=zooKeeper.getData("/config/test2",false,new Stat());

            System.out.println(new String(data));

            while (flag){
                Thread.sleep(500);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }



}
