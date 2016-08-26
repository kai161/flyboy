package com.nk.flyboy.core.service.redis.queue.pub_sub;

import redis.clients.jedis.Jedis;

/**
 * Created by cheris on 2016/8/26.
 */
public class SubClient {

    private String host;

    private int port;

    //�����߸��������ڶ���ģʽҪ��ռredis���ӣ��ʼ��������߶�����ڼ����߳�
    private int subNum;

    //ÿ�������߿��Զ��Ķ��channel
    private String[] channels;

    //�����ߴ�����Ϣ�ķ���
    private QueueHandle queueHandle;

    public SubClient(String host,int port,int subNum){
        this.host=host;
        this.port=port;
        this.subNum=subNum;

    }

    //���ö��ĵ�channel
    public void setChannels(String...channels){
        this.channels=channels;
    }

    //���ô�����Ϣ�ķ���
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
                        //subscribe ��ռjedis����,һ������ֻ������һ��subscribe
                        jedis.subscribe(queueListener,channels);
                    }
                });
                thread.setName("sub"+i);
                thread.start();
            }
        }
    }


}
