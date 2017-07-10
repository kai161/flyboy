package com.nk.flyboy.core.module.zookeeper;

import com.nk.flyboy.core.module.config.WatchRemoteConfig;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

    private static final byte[] defaultData = {0x12, 0x34};

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

        return null;
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

    public static String createNode(String url,String path,String data,CreateMode mode) throws KeeperException, InterruptedException {
        ZooKeeper zooKeeper=getZooKeeper(url);
        if(zooKeeper!=null){

            String[] nodes= StringUtils.tokenizeToStringArray(path,"/",true,true);
            if(nodes.length>0){
                String nodepath="";
                for(int i=0;i<nodes.length-1;i++){
                    nodepath=nodepath+"/"+nodes[i];
                    Stat stat= zooKeeper.exists(nodepath,null);
                    if(stat==null){
                        zooKeeper.create(nodepath,defaultData,ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
                    }
                }
            }
            Stat stat= zooKeeper.exists(path,null);
            if(stat!=null){
                byte[] bytes= zooKeeper.getData(path,null,new Stat());
                if(bytes!=null){
                    String nodeData=new String(bytes);
                    data=nodeData+";"+data;
                }
                zooKeeper.setData(path,data.getBytes(),stat.getVersion()+1);
                return path;
            }else {
                return zooKeeper.create(path,data.getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,mode);
            }
        }
        return null;
    }
}
