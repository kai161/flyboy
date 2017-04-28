package com.nk.flyboy.core.service.redis.lock;

import java.util.concurrent.*;

/**
 * Created on 2017/4/28.
 */
public class Test {

    public static BlockingQueue blockingQueue;
    public static void main(String[] args) {
        RedisLock redisLock=RedisLock.getRedisLock();
        CountDownLatch countDownLatch=new CountDownLatch(100);

        blockingQueue=new ArrayBlockingQueue(100);
        ThreadPoolExecutor poolExecutor=new ThreadPoolExecutor(100, 150, 500, TimeUnit.MILLISECONDS,blockingQueue);

        long start=System.currentTimeMillis();
        for(int i=0;i<100;i++){
            poolExecutor.execute(()->{
                int retryTime=0;
                while (retryTime<1000){
                    if(redisLock.getLock("test")){
                        System.out.println( Thread.currentThread().getName()+ "this thread get the lock");
                        try {
                            Thread.sleep(50);
                            redisLock.releaseLock("test");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        //System.out.println(Thread.currentThread().getName()+" sleep 100 ms");
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    retryTime++;
                }

                System.out.println(Thread.currentThread().getName()+" finish!");
                countDownLatch.countDown();

            });
        }
      /* for (int id=0;id<10;id++){
           boolean d= RedisLock.getLock("test");
       }*/

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end=System.currentTimeMillis();

        System.out.println("cost time : "+(end-start));
        System.out.println("thread over");

        poolExecutor.shutdown();

        Runtime.getRuntime().addShutdownHook(new Thread(()->{

            if(poolExecutor.isShutdown()){
                System.out.println("thread pool close");
                poolExecutor.shutdown();
            }

        }));
    }
}
