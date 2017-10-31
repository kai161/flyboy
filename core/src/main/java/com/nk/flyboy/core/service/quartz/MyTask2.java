package com.nk.flyboy.core.service.quartz;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created on 2017/10/27.
 */
public class MyTask2 {

    static int THREAD_COUNT=5;
    public static ExecutorService executor= Executors.newFixedThreadPool(THREAD_COUNT);
    static int flag=1;

    public static void execute(List<Integer> list){
        int range=list.size()/THREAD_COUNT;
        for(int i=0;i<THREAD_COUNT;i++){
            int start=i*range,end=start+range;
            List<Integer> items;
            if(i==THREAD_COUNT-1){
                items=list.subList(start,list.size());
            }else {
                items=list.subList(start,end);
            }

            executor.execute(()->{
               for(int j=0;j<items.size();j++){
                   if(j%2==flag){
                       System.out.println(items.get(j));
                   }
               }
            });
        }

    }
}
