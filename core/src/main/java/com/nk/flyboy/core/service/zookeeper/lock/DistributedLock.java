package com.nk.flyboy.core.service.zookeeper.lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by cheris on 2016/9/5.
 */
public class DistributedLock implements Lock,Watcher {


    private ZooKeeper zooKeeper;
    private String root="/locks";
    private String lockName;
    private String waitNode;
    private String myZnode;
    private CountDownLatch latch;
    private int sessionTimeout=30000;
    private List<Exception> exceptions=new ArrayList<Exception>();

    public DistributedLock(String config,String lockName){
        this.lockName=lockName;

        try {
            zooKeeper=new ZooKeeper(config,sessionTimeout,this);
            Stat stat=zooKeeper.exists(root,false);

            if(stat==null){
                zooKeeper.create(root,new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

        } catch (IOException e) {
            exceptions.add(e);
        } catch (InterruptedException e) {
            exceptions.add(e);
        } catch (KeeperException e) {
            exceptions.add(e);
        }
    }

    public void lock() {
        if(exceptions.size()>0){
            //throw new LockException()
            throw new LockException(exceptions.get(0));
        }

        try{
            if(this.tryLock()){
                System.out.println("Thread "+Thread.currentThread().getId()+" "+myZnode+" get lock true" );
                return;
            }else {
                waitForLock(waitNode,sessionTimeout);
            }
        }catch (KeeperException e){
            throw new LockException(e);
        }catch (InterruptedException e){
            throw new LockException(e);
        }
    }

    public void lockInterruptibly() throws InterruptedException {
        this.lock();
    }

    public boolean tryLock() {

        String splitStr="_lock_";
        if(lockName.contains(splitStr)){
            throw new LockException("localName can not contains ");
        }

        try {
            myZnode=zooKeeper.create(root+ "/"+lockName+splitStr,new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);

            System.out.println(myZnode+" is created");

            List<String> subNodes=zooKeeper.getChildren(root,false);

            List<String> lockObjNodes=new ArrayList<String>();

            for (String node:subNodes){
                String _node=node.split(splitStr)[0];
                if(_node.equals(lockName)){
                    lockObjNodes.add(node);
                }
            }

            Collections.sort(lockObjNodes);

            System.out.println(myZnode+" == "+lockObjNodes.get(0));

            if(myZnode.equals(root+"/"+lockObjNodes.get(0))){
                return true;
            }

            String subMyZnode=myZnode.substring(myZnode.lastIndexOf("/")+1);

            waitNode=lockObjNodes.get(Collections.binarySearch(lockObjNodes,subMyZnode)-1);

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {

        if(this.tryLock()){
            return true;
        }

        try {
            return waitForLock(waitNode,time);
        } catch (KeeperException e) {
            e.printStackTrace();
        }


        return false;
    }

    private boolean waitForLock(String lower, long time) throws KeeperException, InterruptedException {

        Stat stat=zooKeeper.exists(root+"/"+lower,true);

        if(stat!=null){
            System.out.println(" Thread "+Thread.currentThread().getId()+" waiting for "+root+"/"+lower);

            this.latch=new CountDownLatch(1);
            this.latch.await(time,TimeUnit.MICROSECONDS);

            this.latch=null;
        }

        return true;

    }


    public void unlock() {
        System.out.println("unlock "+myZnode);
        try {
            zooKeeper.delete(myZnode,-1);
            myZnode=null;
            zooKeeper.close();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }


    public Condition newCondition() {
        return null;
    }

    public void process(WatchedEvent watchedEvent) {
        if(this.latch!=null){
            this.latch.countDown();
        }
    }

    public class LockException extends RuntimeException{
        private static final long serialVersionUID=1L;

        public LockException(String e){
            super(e);
        }

        public LockException(Exception e){
            super(e);
        }
    }
}
