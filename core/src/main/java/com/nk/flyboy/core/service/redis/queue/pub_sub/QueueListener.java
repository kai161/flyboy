package com.nk.flyboy.core.service.redis.queue.pub_sub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by cheris on 2016/8/26.
 */
public class QueueListener extends JedisPubSub {

    private Jedis jedis;

    private QueueHandle queueHandle;

    public QueueListener(Jedis jedis,QueueHandle handle) {
        this.jedis=jedis;
        this.queueHandle=handle;
    }

    @Override
    public void onMessage(String channel, String message) {
        //发布者发布quit信息时退出订阅
        if(message.equalsIgnoreCase("quit")){
            this.unsubscribe(channel);
        }

        queueHandle.handle(message);
    }

}
