package com.nk.flyboy.core.service.quartz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/10/27.
 */
public class TaskTest {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<Integer>();

        for (int i = 1; i < 1000000; i++) {
            list.add(i);
        }

        MyTask1.execute(list);
        MyTask2.execute(list);
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            MyTask1.executor.shutdown();
            MyTask2.executor.shutdown();
        }));

        System.exit(0);
    }
}
