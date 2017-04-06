package com.nk.flyboy.core.service.zookeeper.lock;

import org.springframework.context.ApplicationContext;

/**
 * Created by cheris on 2016/9/5.
 */
public class ZKTest {

    public static void main(String[] arg) throws InterruptedException {

        Runnable task1=new Runnable() {
            public void run() {
                DistributedLock lock=null;

                lock=new DistributedLock("192.168.177.130:2191","test1");

                lock.lock();
                try {
                    Thread.sleep(3000);

                    System.out.println("=== Thread "+ Thread.currentThread().getId()+" running");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if(lock!=null){
                        lock.unlock();
                    }
                }
            }
        };

        new Thread(task1).start();

        Thread.sleep(1000);

        ConcurrentTest.ConcurrentTask[] tasks=new ConcurrentTest.ConcurrentTask[60];

        for(int i=0;i<tasks.length;i++){
            ConcurrentTest.ConcurrentTask task3=new ConcurrentTest.ConcurrentTask() {
                public void run() {
                    DistributedLock lock=null;
                    try{
                        lock=new DistributedLock("192.168.177.130:2192","test2");
                        lock.lock();
                        System.out.println(" Thread "+Thread.currentThread().getId()+" running");
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }

                }
            };
            tasks[i]=task3;
        }

        new ConcurrentTest(tasks);
    }
}
