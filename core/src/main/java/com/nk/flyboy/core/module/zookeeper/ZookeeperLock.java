package com.nk.flyboy.core.module.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created on 2017/7/10.
 */
public class ZookeeperLock {

    private static final int DEFAULT_SESSION_TIMEOUT=25000;

    private static String root="/distribute";

    private ZooKeeper zooKeeper;

    private static final byte[] data = {0x12, 0x34};

    private AtomicLong atomicLong;

    private String id;

    private String idName;

    private String ownerId;

    private String lastChildId;

    public ZookeeperLock(String url){
        this.zooKeeper=ZookeeperUtils.getZooKeeper(url);
    }

    public void lock(){
        if(isOwner()){
            return;
        }

        BooleanMutex mutex=new BooleanMutex();
        acquireLock(mutex);

        try {
            mutex.lock();
        }catch (Exception e){
            e.printStackTrace();
            if(!mutex.state()){
                lock();
            }
        }
    }

    public boolean tryLock(){
        if(isOwner()){
            return true;
        }
        acquireLock(null);

        return isOwner();
    }

    public void unlock(){
        if(id!=null){
            try {
                zooKeeper.delete(root+"/"+id,-1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }finally {
                id=null;
            }
        }
    }

    public static String getRoot() {
        return root;
    }

    public String getId() {
        return id;
    }

    private Boolean acquireLock(BooleanMutex mutex) {
        try {
            do{
               if(id==null){
                   long sessionId=zooKeeper.getSessionId();
                   String prefix="x-"+sessionId+"-";

                   String path=zooKeeper.create(root+"/"+prefix,data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

                   int index=path.lastIndexOf("/");
                   id= path.substring(index+1);
                   idName=prefix+id;
               }

               if(id!=null){
                   List<String> names=zooKeeper.getChildren(root,false);
                   if(names.isEmpty()){
                       id=null;
                   }else {
                       SortedSet<String> sortedNames=new TreeSet<>();
                       for (String name:names){
                           sortedNames.add(name);
                       }

                       if(!sortedNames.contains(idName)){
                           id=null;
                           continue;
                       }

                       ownerId=sortedNames.first();
                       if(mutex!=null&&isOwner()){
                           mutex.unlock();
                           return true;
                       }else if(mutex==null){
                           return isOwner();
                       }

                       SortedSet<String> lessThanMe=sortedNames.headSet(idName);
                       if(!lessThanMe.isEmpty()){
                           String lastchildName=lessThanMe.last();

                           Stat stat=zooKeeper.exists(root + "/" + lastchildName, new Watcher() {
                               @Override
                               public void process(WatchedEvent event) {
                                   acquireLock(mutex);
                               }
                           });

                           if(stat==null){
                               acquireLock(mutex);
                           }
                       }else {
                           if(isOwner()){
                               mutex.unlock();
                           }else {
                               id=null;
                           }
                       }
                   }

               }
            }while (id==null);
        }catch (Exception e){

        }

        if(isOwner()&&mutex!=null){
            mutex.unlock();
        }

        return Boolean.FALSE;
    }


    public boolean isOwner(){
        return id!=null&&ownerId!=null&&id.equals(ownerId);
    }


    private class BooleanMutex{

        private Sync sync;

        public BooleanMutex(){
            sync=new Sync();
            set(false);
        }

        public void lock() throws InterruptedException {
            sync.innerLock();
        }

        public void lockTimeout(long timeout, TimeUnit unit) throws TimeoutException, InterruptedException {
            sync.innerLock(unit.toNanos(timeout));
        }

        public void unlock(){
            set(true);
        }

        public void set(Boolean mutex){
            if(mutex){
                sync.innerSetTrue();
            }else {
                sync.innerSetFalse();
            }
        }

        public boolean state(){
            return sync.innerState();
        }

    }

    private final class Sync extends AbstractQueuedSynchronizer{
        private static final int TRUE=1;

        private static final int FALSE=0;

        protected int tryAcquireShared(int arg){
            return getState()==1?1:-1;
        }

        protected boolean tryReleaseShared(int ignore){
            return true;
        }

        private boolean innerState(){
            return getState()==1;
        }

        private void innerLock() throws InterruptedException {
            acquireSharedInterruptibly(0);
        }

        private void innerLock(long nanosTimeout) throws TimeoutException, InterruptedException {
            if(!tryAcquireSharedNanos(0,nanosTimeout)){
                throw new TimeoutException();
            }
        }

        private void innerSetTrue(){
            for (;;){
                int s=getState();
                if(s==TRUE){
                    return;
                }
                if(compareAndSetState(s,TRUE)){
                    releaseShared(0);
                }
            }
        }

        private void innerSetFalse(){
            for (;;){
                int s=getState();
                if(s==FALSE){
                    return;
                }
                if(compareAndSetState(s,FALSE)){
                    setState(FALSE);
                }
            }
        }
    }
}
