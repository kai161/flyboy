package com.nk.flyboy.core.service.redis.queue;


import com.nk.flyboy.core.util.RedisUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by cheris on 2016/8/25.
 */
@Component
public class Product {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisUtil redisUtil;

    public void setKey(){
       Set<String> set= stringRedisTemplate.keys("*");
        if(!set.isEmpty()){
            for(String item:set){
                System.out.println(item);
            }
        }

        ValueOperations<String,String> stringOps= stringRedisTemplate.opsForValue();
        String value= stringOps.get("hello");

        System.out.println(value);

        redisUtil.set(stringRedisTemplate,"testJava","jedis");


    }

}
