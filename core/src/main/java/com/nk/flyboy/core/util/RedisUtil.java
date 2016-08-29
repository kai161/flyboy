package com.nk.flyboy.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
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
        return redisTemplate.expire(key, expireTime, unit);
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
        return set(redisTemplate, key, value, null, null);
    }

    /*
    * ����key��value��������key�Ĺ���ʱ��
    * @param TimeUnit ����ʱ��ĵ�λ
    * */
    public boolean set(RedisTemplate redisTemplate,Object key,Object value,Long expireTime,TimeUnit unit){
        boolean result=false;
        try{
            ValueOperations operations=redisTemplate.opsForValue();
            operations.set(key, value);
            if(expireTime!=null){
                expire(redisTemplate,key,expireTime,unit);
            }
            result=true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("set key {} value {} error {}",key,value,e.getMessage());
        }
        return result;
    }

    /*
    *����hash���͵�key field value
    */
    public boolean hSet(RedisTemplate redisTemplate,Object key,Object field,Object value){
        return hSet(redisTemplate,key,field,value,null,null);
    }

    /*
    * ����hash���͵�key field value
    * ������key�Ĺ���ʱ��
    */
    public boolean hSet(RedisTemplate redisTemplate,Object key,Object field,Object value,Long expireTime,TimeUnit unit){
        boolean result=false;
        try{
            HashOperations operations=redisTemplate.opsForHash();
            operations.put(key, field, value);
            if(expireTime!=null){
                expire(redisTemplate,key,expireTime,unit);
            }
            result=true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("hSet key {} field {} value {} error{}",key,field,value,e.getMessage());
        }
        return result;
    }

    /*
    *����hash���͵�key������map����ʽ����field��value
    */
    public boolean hSetAll(RedisTemplate redisTemplate,Object key,Map map){

        return hSetAll(redisTemplate, key, map, null, null);
    }

    /*
    *����hash���͵�key������map����ʽ����field��value
    * ������key�Ĺ���ʱ��
    */
    public boolean hSetAll(RedisTemplate redisTemplate,Object key,Map map,Long expireTime,TimeUnit unit){
        boolean result=false;
        try{
            HashOperations operations=redisTemplate.opsForHash();
            operations.putAll(key, map);
            if(expireTime!=null){
                expire(redisTemplate,key,expireTime,unit);
            }
            result=true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("hSetAll key {} map {} error{}",key,map.toString(),e.getMessage());
        }
        return result;
    }

    /*
    *��ȡhash���� ָ��key��fieldֵ
    */
    public Object hGet(RedisTemplate redisTemplate,Object key,Object field){
        Object value=null;
        try{
            HashOperations operations=redisTemplate.opsForHash();
            value=operations.get(key, field);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("hGet key {} field {} error {}",key,field,e.getMessage());
        }
        return value;
    }

    /*
    * ��ȡhash���� ָ��key������ֵ
    */
    public Map hGetAll(RedisTemplate redisTemplate,Object key){
        Map map=null;
        try{
            HashOperations operations=redisTemplate.opsForHash();
            map=operations.entries(key);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("hGet key {} field {} error {}",key,e.getMessage());
        }
        return map;
    }

    /*
    *����������list���� key�б��ͷԪ��
    */
    public Object lPop(RedisTemplate redisTemplate,Object key){
        Object value=null;
        try{
            ListOperations operations=redisTemplate.opsForList();
            value= operations.leftPop(key);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("lPOP key {} error {}",key,e.getMessage());
        }
        return value;
    }

    /*
    *��valueֵ�����б�key�ı�ͷ
    */
    public boolean lPush(RedisTemplate redisTemplate,Object key,Object value){
        return lPush(redisTemplate,key,value,null,null);
    }

    /*
    *��valueֵ�����б�key�ı�ͷ
    * ����key���ù���ʱ��
    */
    public boolean lPush(RedisTemplate redisTemplate,Object key,Object value,Long expireTime,TimeUnit unit){
        boolean result=false;
        try{
            ListOperations operations=redisTemplate.opsForList();
            operations.leftPush(key, value);
            if(expireTime!=null){
                expire(redisTemplate,key,expireTime,unit);
            }
            result=true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("lPush key {} value {} error{}",key,value,e.getMessage());
        }
        return result;
    }

    /*
    *��list�����е�Ԫ�ض������б�key�ı�ͷ
    */
    public boolean lPushAll(RedisTemplate redisTemplate,Object key,Collection list){
        return lPushAll(redisTemplate,key,list,null,null);
    }

    /*
    *��list�����е�Ԫ�ض������б�key�ı�ͷ
    * ������key�Ĺ���ʱ��
    */
    public boolean lPushAll(RedisTemplate redisTemplate,Object key,Collection list,Long expireTime,TimeUnit unit){
        boolean result=false;
        try{
            ListOperations operations=redisTemplate.opsForList();
            operations.leftPushAll(key, list);
            if(expireTime!=null){
                expire(redisTemplate,key,expireTime,unit);
            }
            result=true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("lPushAll key {} list {} error{}",key,list.toString(),e.getMessage());
        }
        return result;
    }

    /*
    * ��ȡ�б�keyָ�������Ԫ�أ���0��ʼ��
    */
    public List lRang(RedisTemplate redisTemplate,Object key,Long start,Long end){
        List list=null;
        try{
            ListOperations operations=redisTemplate.opsForList();
            list=operations.range(key,start,end);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("lRang key {} start{} end {} error {}",key,start,end,e.getMessage());
        }
        return  list;
    }

    /*
    *���ݲ��� count ��ֵ���Ƴ��б�������� value ��ȵ�Ԫ�ء�
    * count > 0 : �ӱ�ͷ��ʼ���β�������Ƴ��� value ��ȵ�Ԫ�أ�����Ϊ count ��
    * count < 0 : �ӱ�β��ʼ���ͷ�������Ƴ��� value ��ȵ�Ԫ�أ�����Ϊ count �ľ���ֵ��
    * count = 0 : �Ƴ����������� value ��ȵ�ֵ��
    */
    public boolean lRem(RedisTemplate redisTemplate,Object key,Long count,Object value){
        boolean result=false;
        try{
            ListOperations operations=redisTemplate.opsForList();
            operations.remove(key,count,value);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("lRem key {} error{}",key,e.getMessage());
        }
        return result;
    }

    /*
    *����������list���� key�б��βԪ��
    */
    public Object rPop(RedisTemplate redisTemplate,Object key){
        Object value=null;
        try{
            ListOperations operations=redisTemplate.opsForList();
            value = operations.rightPop(key);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("rPOP key {} error {}",key,e.getMessage());
        }
        return value;
    }

    /*
    *��valueԪ�ض������б�key�ı�β
    */
    public boolean rPush(RedisTemplate redisTemplate,Object key,Object value){
        return rPush(redisTemplate,key,value,null,null);
    }

    /*
   *��valueԪ�ض������б�key�ı�β
   * ������key�Ĺ���ʱ��
   */
    public boolean rPush(RedisTemplate redisTemplate,Object key,Object value,Long expireTime,TimeUnit unit ){
        boolean result=false;
        try{
            ListOperations operations=redisTemplate.opsForList();
            operations.rightPush(key,value);
            if(expireTime!=null){
                expire(redisTemplate,key,expireTime,unit);
            }
            result=true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("rPush key {} value {} error {}",key,value,e.getMessage());
        }
        return result;
    }

    /*
   *��list�����е�Ԫ�ض������б�key�ı�β
   */
    public boolean rPushAll(RedisTemplate redisTemplate,Object key,Collection list){
        return rPushAll(redisTemplate,key,list,null,null);
    }

    /*
   *��list�����е�Ԫ�ض������б�key�ı�β
   * ������key�Ĺ���ʱ��
   */
    public boolean rPushAll(RedisTemplate redisTemplate,Object key,Collection list,Long expireTime,TimeUnit unit){
        boolean result=false;
        try{
            ListOperations operations=redisTemplate.opsForList();
            operations.rightPushAll(key, list);
            if(expireTime!=null){
                expire(redisTemplate,key,expireTime,unit);
            }
            result=true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("rPushAll key {} List {} error {}",key,list.toString(),e.getMessage());
        }
        return result;
    }

    /*
    *���б� key �±�Ϊ index ��Ԫ�ص�ֵ����Ϊ value
    */
    public boolean lSet(RedisTemplate redisTemplate,Object key,Long index,Object value){
        boolean result=false;
        try{
            ListOperations operations=redisTemplate.opsForList();
            operations.set(key, index, value);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("lSet key {} index {} value {} error {}",key,index,value,e.getMessage());
        }
        return result;
    }

    /*
    *  һ��ԭ�Ӳ�����������������
    * 1�����б� sourceKey �е����һ��Ԫ��(βԪ��)�����������ظ��ͻ��ˡ�
    * 2���� sourceKey ������Ԫ�ز��뵽�б� destinationKey ����Ϊ destinationKey �б�ĵ�ͷԪ�ء�
    */
    public Object rPopLPush(RedisTemplate redisTemplate,Object sourceKey,Object destinationKey){
        Object value=null;
        try{
            ListOperations operations=redisTemplate.opsForList();
            value=operations.rightPopAndLeftPush(sourceKey, destinationKey);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("rPopLPush sourceKey {} destinationKey {} error {}",sourceKey,destinationKey,e.getMessage());
        }
        return value;
    }

    /*
    *��set���͵�key���������member���ظ���memberֻ���һ��
    */
    public boolean sAdd(RedisTemplate redisTemplate,Object key,Object... members){
        boolean result=false;
        try{
            SetOperations operations=redisTemplate.opsForSet();
            operations.add(key,members);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("sAdd key {} member {} error {}",key,members.toString(),e.getMessage());
        }
        return result;
    }

    /*
    *�Ƴ�����key��ָ����Ԫ�أ��������Ƴ��ĸ���
    */
    public Long sRem(RedisTemplate redisTemplate,Object key,Object... values){
        Long result=0L;
        try{
            SetOperations operations=redisTemplate.opsForSet();
            result=operations.remove(key, values);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("sRem key {} values {} error {}",key,values.toString(),e.getMessage());
        }
        return result;
    }

    /*
    *������key�е�Ԫ��value�ƶ�������destKey��
    */
    public boolean sMove(RedisTemplate redisTemplate,Object key,Object value,Object destKey){
        boolean result=false;
        try{
            SetOperations operations=redisTemplate.opsForSet();
            result= operations.move(key, value, destKey);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("sMove key {} value {} destKey {} error {}",key,value,destKey,e.getMessage());
        }
        return result;
    }

    /*
    *�Ƚ�����set���ϼ�Ĳ������key �����еĲ
    */
    public Set sDiff(RedisTemplate redisTemplate,Object key,Object otherKey){
        Set set=null;
        try{
            SetOperations operations=redisTemplate.opsForSet();
            set=operations.difference(key, otherKey);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("sDiff key {} other {} error {}",key,otherKey,e.getMessage());
        }
        return set;
    }

    /*
    * �Ƚ�����set���ϼ�Ĳ��������key �����еĲ���浽destKey������
    */
    public Long sDiffStore(RedisTemplate redisTemplate,Object key,Object otherKey,Object destKey){
        Long result=0L;
        try{
            SetOperations operations=redisTemplate.opsForSet();
            result=operations.differenceAndStore(key, otherKey, destKey);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("sDiffStore key {} other {} destKey {} error {}",key,otherKey, destKey, e.getMessage());
        }
        return result;
    }

    /*
    * �Ƚ�����set���ϼ�Ľ��������ؽ�����Ա
    */
    public Set sInter(RedisTemplate redisTemplate,Object key,Object otherKey){
        Set set=null;
        try{
            SetOperations operations=redisTemplate.opsForSet();
            set=operations.intersect(key, otherKey);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("sInter key {} other {} error {}",key,otherKey,e.getMessage());
        }
        return set;
    }

    /*
    * �Ƚ�����set���ϼ�Ľ��������ؽ�����Ա��ָ����destKey������
    */
    public Long sInterStore(RedisTemplate redisTemplate,Object key,Object otherKey,Object destKey){
        Long result=0L;
        try{
            SetOperations operations=redisTemplate.opsForSet();
            result=operations.intersectAndStore(key, otherKey, destKey);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("sInterStore key {} other {} destKey {} error {}",key,otherKey, destKey, e.getMessage());
        }
        return result;
    }

    /*
    *ȡ����set���ϵĲ��������ز�����Ա
    */
    public Set sUnion(RedisTemplate redisTemplate,Object key,Object otherKey){
        Set set=null;
        try{
            SetOperations operations=redisTemplate.opsForSet();
            set=operations.union(key, otherKey);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("sUnion key {} other {} error {}",key,otherKey,e.getMessage());
        }
        return set;
    }

    /*
    *ȡ����set���ϵĲ��������ز�����Ա��ָ����destKey������
    */
    public Long sUnionStore(RedisTemplate redisTemplate,Object key,Object otherKey,Object destKey){
        Long result=0L;
        try{
            SetOperations operations=redisTemplate.opsForSet();
            result=operations.unionAndStore(key, otherKey, destKey);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("sUnionStore key {} other {} destKey {} error {}",key,otherKey, destKey, e.getMessage());
        }
        return result;
    }

    /*
    *��sortedSet ����ӳ�Աvalue��ָ����scoreֵ
    */
    public boolean zAdd(RedisTemplate redisTemplate,Object key,Object value,double score){
        boolean result=false;
        try{
            ZSetOperations operations=redisTemplate.opsForZSet();
            result= operations.add(key, value, score);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("zAdd key {} value {} score {} error {}",key,value,score,e.getMessage());
        }
        return result;
    }

    /*
    *�������� key �У� score ֵ�� min �� max ֮��(Ĭ�ϰ��� score ֵ���� min �� max )�ĳ�Ա��������
    */
    public Long zCount(RedisTemplate redisTemplate,Object key,double min,double max){
        Long result=0L;
        try{
            ZSetOperations operations=redisTemplate.opsForZSet();
            result=operations.count(key, min, max);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("zCount key {} min {} max {} error {}",key,min,max,e.getMessage());
        }
        return result;
    }

    /*
    * Ϊ���� key �ĳ�Ա member �� score ֵ�������� delta ��
    */
    public double zIncrBy(RedisTemplate redisTemplate,Object key,Object value, double delta){
        double result=0;
        try{
            ZSetOperations operations=redisTemplate.opsForZSet();
            result= operations.incrementScore(key, value, delta);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("zIncrBy key {} value {} delta {} error {}",key,value,delta,e.getMessage());
        }
        return result;
    }

    /*
    *�������� key �У����� score ֵ���� min �� max ֮��(�������� min �� max )�ĳ�Ա�����򼯳�Ա�� score ֵ����(��С����)�������С�
    */
    public Set zRangeByScore(RedisTemplate redisTemplate,Object key,double min,Long max){
        Set set=null;
        try{
            ZSetOperations operations=redisTemplate.opsForZSet();
            set=operations.rangeByScore(key, min, max);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("zRangeByScore key {} min {} max {} error {}",key,min,max,e.getMessage());
        }
        return set;
    }

    /*
    *�������� key �г�Ա member ���������������򼯳�Ա�� score ֵ����(��С����)˳�����С������� 0 Ϊ�ף�Ҳ����˵�� score ֵ��С�ĳ�Ա����Ϊ 0 ��
    */
    public Long zRank(RedisTemplate redisTemplate,Object key,Object value){
        Long result=0L;
        try{
            ZSetOperations operations=redisTemplate.opsForZSet();
            result=operations.rank(key, value);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("zRank key {} value {} error {}",key,value,e.getMessage());
        }
        return result;
    }

    /*
    *�������� key �У���Ա member �� score ֵ��
    */
    public double zScore(RedisTemplate redisTemplate,Object key,Object value){
        double result=0;
        try{
            ZSetOperations operations=redisTemplate.opsForZSet();
            result= operations.score(key, value);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("zScore key {} value {}  error {}",key,value,e.getMessage());
        }
        return result;
    }

    /*
    *�Ƴ����� key �У����� score ֵ���� min �� max ֮��(�������� min �� max )�ĳ�Ա��
    */
    public Long zRemrangeByScore(RedisTemplate redisTemplate,Object key,double min ,double max){
        Long result=0L;
        try{
            ZSetOperations operations=redisTemplate.opsForZSet();
            result=operations.removeRangeByScore(key, min, max);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("zRemrangeByScore key {} min {} max {} error {}",key,min,max,e.getMessage());
        }
        return result;
    }

    /*
    *�������� key �У� score ֵ���� max �� min ֮��(Ĭ�ϰ������� max �� min )�����еĳ�Ա�����򼯳�Ա�� score ֵ�ݼ�(�Ӵ�С)�Ĵ������С�
    */
    public Set zRevrangeByScore(RedisTemplate redisTemplate,Object key,double min,double max){
        Set set=null;
        try{
            ZSetOperations operations=redisTemplate.opsForZSet();
            set=operations.reverseRangeByScore(key, min, max);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("zRevrangeByScore key {} min {} max {} error {}",key,min,max,e.getMessage());
        }
        return set;
    }

    /*
    *ȡ����set���ϵĽ������浽ָ����destkey�У��������ĳ����Ա�� score ֵ�����и������¸ó�Ա score ֵ֮��.
    */
    public Long zInterStore(RedisTemplate redisTemplate,Object key,Object otherKey,Object destKey){
        Long result=0L;
        try{
            ZSetOperations operations=redisTemplate.opsForZSet();
            result=operations.intersectAndStore(key, otherKey, destKey);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("zInterstore key {} otherKey {} destKey {} error {}",key,otherKey,destKey,e.getMessage());
        }
        return result;
    }

    /*
    *ȡ����set���ϵĲ��������浽ָ����destKey��
    */
    public Long zUnionStore(RedisTemplate redisTemplate,Object key,Object otherKey,Object destKey){
        Long result=0L;
        try{
            ZSetOperations operations=redisTemplate.opsForZSet();
            result=operations.unionAndStore(key, otherKey, destKey);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("zUnionStore key {} otherKey {} destKey {} error {}",key,otherKey,destKey,e.getMessage());
        }
        return result;
    }

}
