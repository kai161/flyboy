package com.nk.flyboy.core.service.redis.queue.pub_sub;

import redis.clients.jedis.Jedis;

/**
 * Created by cheris on 2016/8/26.
 */
public class SubClient {

    private String host;

    private int port;

    //订阅者个数，由于订阅模式要独占redis链接，故几个订阅者都相对于几个线程
    private int subNum;

    //每个订阅者可以订阅多个channel
    private String[] channels;

    //订阅者处理消息的方法
    private QueueHandle queueHandle;

    public SubClient(String host,int port,int subNum){
        this.host=host;
        this.port=port;
        this.subNum=subNum;

    }

    //设置订阅的channel
    public void setChannels(String...channels){
        this.channels=channels;
    }

    //设置处理消息的方法
    public void setQueueHandle(QueueHandle queueHandle){
        this.queueHandle=queueHandle;
    }

    public void start(){
        if(subNum>0){
            for(int i=0;i<subNum;i++){
                Thread thread=new Thread(new Runnable() {
                    public void run() {
                        Jedis jedis=new Jedis(host,port);
                        QueueListener queueListener=new QueueListener(jedis,queueHandle);
                        //subscribe 独占jedis链接,一个进程只能运行一个subscribe
                        jedis.subscribe(queueListener,channels);
                    }
                });
                thread.setName("sub"+i);
                thread.start();
            }
        }
    }


}
