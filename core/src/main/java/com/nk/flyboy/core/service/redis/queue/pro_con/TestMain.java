package com.nk.flyboy.core.service.redis.queue.pro_con;

/**
 * Created by cheris on 2016/8/30.
 */
public class TestMain {

    public static void main(String[] args) throws InterruptedException {
        String host="172.16.184.128";
        int port=6379;
        String queueKey="queueTest";
        String queueKey1="queueTest1";

        Product product=new Product(host,port);
        Consumer consumer=new Consumer(host,port);

        consumer.start(queueKey, queueKey1);

        Thread.sleep(100L);

        for(int i=0;i<10;i++){
            product.push(queueKey,"this is queueKey --"+i);
            product.push(queueKey1,"this is queueKey1 --"+i);
            Thread.sleep(100L);
        }

        product.push(queueKey, "quit");
        product.push(queueKey1, "quit");

        for(int i=10;i<20;i++){
            //product.push(queueKey,"this is queueKey --"+i);
            product.push(queueKey1,"this is queueKey1 --"+i);
            Thread.sleep(100L);
        }

    }
}
