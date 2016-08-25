package com.nk.flyboy.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by cheris on 2016/8/25.
 */
@Component
public class RedisUtil {

    Logger logger= LoggerFactory.getLogger(RedisUtil.class);


    /**
     * 判断是否有对应的value
     */
    public boolean exists(RedisTemplate redisTemplate,Object key){
        return redisTemplate.hasKey(key);
    }

    /*
    * 删除对应的value
    *
    * */
    public void remove(RedisTemplate redisTemplate, Object key){
        if(exists(redisTemplate,key)){
            redisTemplate.delete(key);
        }else {
            logger.info("key {} not exist",key);
        }
    }

    /*
    * 根据表达式批量删除对应的value
    * */
    public void removePattern(RedisTemplate redisTemplate, String pattern){
        Set<Object> keys=redisTemplate.keys(pattern);
        if(keys.size()>0){
            redisTemplate.delete(keys);
        }else {
            logger.info("pattern {} for the key is not exist",pattern);
        }

    }

    /*
    * 批量删除对应的value
    * */
    public void remove(RedisTemplate redisTemplate,  Object... keys){
        for (Object key:keys){
            remove(redisTemplate,key);
        }
    }

    /*
    * 批量删除集合中的value
    * */
    public void remove(RedisTemplate redisTemplate,Collection<Object> keys){
        redisTemplate.delete(keys);
    }


    /*
    * 获取key对应的redis类型
    * */
    public DataType type(RedisTemplate redisTemplate,Object key){
        return redisTemplate.type(key);
    }

    /*
    * 设置key的过期时间
    * */
    public boolean expire(RedisTemplate redisTemplate, Object key,Long expireTime,TimeUnit unit){
        return redisTemplate.expire(key,expireTime,unit);
    }

    /*
    * 获取key 对应的value
    * */
    public Object get(RedisTemplate redisTemplate, Object key){
        ValueOperations<String,String> ops=redisTemplate.opsForValue();
        return ops.get(key);

    }

    /*
    * 设置key和value
    * */
    public boolean set(RedisTemplate redisTemplate, Object key,Object value){
        boolean result=false;
        try{
            ValueOperations operations=redisTemplate.opsForValue();
            operations.set(key,value);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("set key {} value {} error {}",key,value,e.getMessage());
        }
        return result;
    }

    /*
    * 设置key和value，并设置key的过期时间
    * @param TimeUnit 过期时间的单位
    * */
    public boolean set(RedisTemplate redisTemplate,Object key,Object value,Long expireTime,TimeUnit unit){
        boolean result=false;
        try{
            ValueOperations operations=redisTemplate.opsForValue();
            operations.set(key,value);
            redisTemplate.expire(key,expireTime, unit);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("set key {} value {} error {}",key,value,e.getMessage());
        }
        return result;
    }

}
