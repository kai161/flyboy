package com.nk.flyboy.core.service.redis.queue.pub_sub;

/**
 * Created by cheris on 2016/8/26.
 */
public class TestPubSub {

    static class MyQueueHandle implements QueueHandle{

        public void handle(String message) {
            System.out.println(message+"----"+Thread.currentThread().getName());
        }
    }

    public static void main(String[] arg) throws InterruptedException {
        String host="192.168.177.130";
        int port=6390;
        String channel="testChannel";

        PubClient pubClient=new PubClient(host,port);

        //订阅之前发布的信息订阅者是无法接收到的
        pubClient.pub(channel, "before1");
        pubClient.pub(channel, "before2");

        Thread.sleep(100);

        SubClient subClient=new SubClient(host,port,2);
        subClient.setChannels(channel);
        subClient.setQueueHandle(new MyQueueHandle());
        subClient.start();

        Thread.sleep(100);
        for(int i=0;i<10;i++){
            String msg="publish--"+i;
            pubClient.pub(channel,msg);

            Thread.sleep(1000);
        }

        //关闭pub
        pubClient.close(channel);

    }
}
