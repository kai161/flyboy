package com.nk.flyboy.core.module.zookeeper;

import com.nk.flyboy.core.module.config.WatchRemoteConfig;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created on 2017/7/3.
 */
@Component
public class ZookeeperUtils {

    private static String zookeeperUrl="";
    private static ZooKeeper zooKeeper;

    public static ZooKeeper getZooKeeper(String url){
        try {
            zooKeeper=new ZooKeeper(url, 25000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if(watchedEvent.getState()== Event.KeeperState.SyncConnected){
                        System.out.println("zookeeper connect ....");
                    }
                }
            });
            zookeeperUrl=url;
        }catch (Exception e){
            e.printStackTrace();
        }
        return zooKeeper;
    }

    public static byte[] getNodeData(String url,String nodePath,Watcher watcher){
        byte[] bytes=null;
        ZooKeeper zooKeeper=getZooKeeper(url);
        if(zooKeeper!=null){
            try {
                bytes=zooKeeper.getData(nodePath,watcher,new Stat());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return bytes;
    }

    public static byte[] getNodeAndChildrenData(String url,String nodePath,Watcher watcher){
        byte[] bytes=null;
        ZooKeeper zooKeeper=getZooKeeper(url);
        if(zooKeeper!=null){
            try {
                StringBuilder sb=new StringBuilder();
                getNodeAndChildrenDate(zooKeeper,nodePath,watcher,sb);
                return sb.toString().getBytes();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return bytes;
    }

    public static void getNodeAndChildrenDate(ZooKeeper zooKeeper,String nodePath,Watcher watcher,StringBuilder sb) throws KeeperException, InterruptedException {
        byte[] nodeData=zooKeeper.getData(nodePath,watcher,new Stat());
        sb.append( new String(nodeData)).append(";");
        List<String> children=zooKeeper.getChildren(nodePath,null);
        if(children!= null&&children.size()>0){
            for (String child:children){
                String path=nodePath+"/"+child;
                getNodeAndChildrenDate(zooKeeper,path,watcher,sb);
            }
        }
    }
}
