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
     * �ж��Ƿ��ж�Ӧ��value
     */
    public boolean exists(RedisTemplate redisTemplate,Object key){
        return redisTemplate.hasKey(key);
    }

    /*
    * ɾ����Ӧ��value
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
    * ���ݱ��ʽ����ɾ����Ӧ��value
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
    * ����ɾ����Ӧ��value
    * */
    public void remove(RedisTemplate redisTemplate,  Object... keys){
        for (Object key:keys){
            remove(redisTemplate,key);
        }
    }

    /*
    * ����ɾ�������е�value
    * */
    public void remove(RedisTemplate redisTemplate,Collection<Object> keys){
        redisTemplate.delete(keys);
    }


    /*
    * ��ȡkey��Ӧ��redis����
    * */
    public DataType type(RedisTemplate redisTemplate,Object key){
        return redisTemplate.type(key);
    }

    /*
    * ����key�Ĺ���ʱ��
    * */
    public boolean expire(RedisTemplate redisTemplate, Object key,Long expireTime,TimeUnit unit){
        return redisTemplate.expire(key,expireTime,unit);
    }

    /*
    * ��ȡkey ��Ӧ��value
    * */
    public Object get(RedisTemplate redisTemplate, Object key){
        ValueOperations<String,String> ops=redisTemplate.opsForValue();
        return ops.get(key);

    }

    /*
    * ����key��value
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
    * ����key��value��������key�Ĺ���ʱ��
    * @param TimeUnit ����ʱ��ĵ�λ
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
