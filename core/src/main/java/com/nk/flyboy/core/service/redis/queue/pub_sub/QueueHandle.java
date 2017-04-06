package com.nk.flyboy.core.service.redis.queue.pub_sub;

/**
 * Created by cheris on 2016/8/26.
 */
public interface QueueHandle {

    void handle(String msg);
}
