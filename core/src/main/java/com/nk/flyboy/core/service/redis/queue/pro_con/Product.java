package com.nk.flyboy.core.service.redis.queue.pro_con;

import redis.clients.jedis.Jedis;

/**
 * Created by cheris on 2016/8/30.
 */
public class Product {

    private Jedis jedis;

    public Product(String host,int port) {
        this.jedis = new Jedis(host, port);
    }

    public void push(String key,String messsage){
        jedis.lpush(key,messsage);
    }
}
