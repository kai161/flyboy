package com.nk.flyboy.core.service.zookeeper.configchangetest;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;

/**
 * Created by cheris on 2016/9/5.
 */
public class ActiveKeyValueStore extends ConnectionWatch  {

    private static final Charset CHARSET=Charset.forName("UTF-8");

    public void write(String path,String value) throws KeeperException, InterruptedException {
        Stat stat=zooKeeper.exists(path,this);

        if(stat==null){
            zooKeeper.create(path,value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }else {
            zooKeeper.setData(path,value.getBytes(CHARSET),-1);
        }
    }

    public String read(String path,Watcher watcher) throws KeeperException, InterruptedException {
        byte[] data=zooKeeper.getData(path,watcher,null);
        return new String(data,CHARSET);
    }

}
