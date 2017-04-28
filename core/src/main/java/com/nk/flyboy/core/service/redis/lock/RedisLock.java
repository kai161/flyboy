package com.nk.flyboy.core.service.redis.lock;

import com.sun.org.apache.xml.internal.security.Init;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created on 2017/4/28.
 */
public class RedisLock {

    private static volatile RedisLock redisLock;

    private RedisLock(){}

    public static RedisLock getRedisLock(){
        if(redisLock==null){
            synchronized (RedisLock.class){
                redisLock=new RedisLock();
                init();
            }
        }

        return redisLock;
    }

    public static JedisPool pool;

    private static void init() {
        String host="172.16.184.128";
        int port=6379;
        //JedisPoolConfig config=new JedisPoolConfig();
        //config.setMaxWaitMillis(1000);

        pool=new JedisPool(host,port);

    }

    public boolean getLock(String key){
        try(Jedis jedis=pool.getResource();) {
            return jedis.setnx(key, "1") > 0;
        }
    }

    public void releaseLock(String key){
        try (Jedis jedis=pool.getResource();){
            jedis.del(key);
        }
    }
}
