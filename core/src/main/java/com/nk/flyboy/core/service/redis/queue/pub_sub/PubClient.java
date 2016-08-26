package com.nk.flyboy.core.service.redis.queue.pub_sub;

import redis.clients.jedis.Jedis;

/**
 * Created by cheris on 2016/8/26.
 */
public class PubClient {

    private Jedis jedis;

    public PubClient(String host,int port){
        this.jedis=new Jedis(host,port);
    }

    //发布消息
    public void pub(String channel,String msg){
        jedis.publish(channel,msg);
    }

    //关闭发布通道
    public void close(String channel){
        jedis.publish(channel,"quit");
        jedis.del(channel);
    }
}
