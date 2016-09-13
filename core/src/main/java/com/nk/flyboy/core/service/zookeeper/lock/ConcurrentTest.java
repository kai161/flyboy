package com.nk.flyboy.core.service.zookeeper.lock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by cheris on 2016/9/5.
 */
public class ConcurrentTest {

    private CountDownLatch startSignal=new CountDownLatch(1);
    private CountDownLatch doneSignal=null;
    private CopyOnWriteArrayList<Long> list=new CopyOnWriteArrayList<Long>();
    private AtomicInteger err=new AtomicInteger();
    private ConcurrentTask[] tasks=null;

    public interface ConcurrentTask{
        void run();
    }

    public ConcurrentTest(ConcurrentTask...tasks){
        this.tasks=tasks;
        if(tasks==null){
            System.out.println(" task can not null");
            System.exit(1);
        }

        doneSignal=new CountDownLatch(tasks.length);

        start();

    }

    public void start(){

        createThread();
        startSignal.countDown();

        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getExeTime();
    }


    public void createThread(){
        long len=doneSignal.getCount();
        for(int i=0;i<len;i++){
            final int j=i;
            new Thread(new Runnable() {
                public void run() {
                    try {
                        startSignal.await();
                        long start=System.currentTimeMillis();
                        tasks[j].run();
                        long end=(System.currentTimeMillis()-start);
                        list.add(end);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    doneSignal.countDown();
                }
            }).start();
        }
    }

    private void getExeTime(){
        int size=list.size();
        List<Long> _list=new ArrayList<Long>(size);
        _list.addAll(list);
        Collections.sort(_list);
        long min=_list.get(0);
        long max=_list.get(size-1);
        long sum=0L;
        for(Long t:_list){
            sum+=t;
        }
        long avg=sum/size;
        System.out.println("min :" +min);
        System.out.println("max :"+max);
        System.out.println("avg :"+avg);
        System.out.println("err :"+ err.get());
    }


}
