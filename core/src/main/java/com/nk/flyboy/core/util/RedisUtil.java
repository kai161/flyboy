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
        return redisTemplate.expire(key, expireTime, unit);
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
        return set(redisTemplate, key, value, null, null);
    }

    /*
    * 设置key和value，并设置key的过期时间
    * @param TimeUnit 过期时间的单位
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
    *设置hash类型的key field value
    */
    public boolean hSet(RedisTemplate redisTemplate,Object key,Object field,Object value){
        return hSet(redisTemplate,key,field,value,null,null);
    }

    /*
    * 设置hash类型的key field value
    * 并设置key的过期时间
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
    *设置hash类型的key，并以map的形式保存field和value
    */
    public boolean hSetAll(RedisTemplate redisTemplate,Object key,Map map){

        return hSetAll(redisTemplate, key, map, null, null);
    }

    /*
    *设置hash类型的key，并以map的形式保存field和value
    * 并设置key的过期时间
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
    *获取hash类型 指定key的field值
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
    * 获取hash类型 指定key的所有值
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
    *弹出并返回list类型 key列表的头元素
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
    *将value值插入列表key的表头
    */
    public boolean lPush(RedisTemplate redisTemplate,Object key,Object value){
        return lPush(redisTemplate,key,value,null,null);
    }

    /*
    *将value值插入列表key的表头
    * 并给key设置过期时间
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
    *将list集合中的元素都插入列表key的表头
    */
    public boolean lPushAll(RedisTemplate redisTemplate,Object key,Collection list){
        return lPushAll(redisTemplate,key,list,null,null);
    }

    /*
    *将list集合中的元素都插入列表key的表头
    * 并设置key的过期时间
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
    * 获取列表key指定区间的元素（从0开始）
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
    *根据参数 count 的值，移除列表中与参数 value 相等的元素。
    * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
    * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
    * count = 0 : 移除表中所有与 value 相等的值。
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
    *弹出并返回list类型 key列表的尾元素
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
    *将value元素都插入列表key的表尾
    */
    public boolean rPush(RedisTemplate redisTemplate,Object key,Object value){
        return rPush(redisTemplate,key,value,null,null);
    }

    /*
   *将value元素都插入列表key的表尾
   * 并设置key的过期时间
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
   *将list集合中的元素都插入列表key的表尾
   */
    public boolean rPushAll(RedisTemplate redisTemplate,Object key,Collection list){
        return rPushAll(redisTemplate,key,list,null,null);
    }

    /*
   *将list集合中的元素都插入列表key的表尾
   * 并设置key的过期时间
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
    *将列表 key 下标为 index 的元素的值设置为 value
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
    *  一个原子操作包含两个动作：
    * 1、将列表 sourceKey 中的最后一个元素(尾元素)弹出，并返回给客户端。
    * 2、将 sourceKey 弹出的元素插入到列表 destinationKey ，作为 destinationKey 列表的的头元素。
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
    *向set类型的key集合中添加member，重复的member只添加一次
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
    *移除集合key中指定的元素，并返回移除的个数
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
    *将集合key中的元素value移动到集合destKey中
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
    *比较两个set集合间的差集并返回key 集合中的差集
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
    * 比较两个set集合间的差集并将返回key 集合中的差集保存到destKey集合中
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
    * 比较两个set集合间的交集并返回交集成员
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
    * 比较两个set集合间的交集并返回交集成员到指定的destKey集合中
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
    *取两个set集合的并集并返回并集成员
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
    *取两个set集合的并集并返回并集成员到指定的destKey集合中
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
    *向sortedSet 中添加成员value并指定的score值
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
    *返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
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
    * 为有序集 key 的成员 member 的 score 值加上增量 delta 。
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
    *返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。
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
    *返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。排名以 0 为底，也就是说， score 值最小的成员排名为 0 。
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
    *返回有序集 key 中，成员 member 的 score 值。
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
    *移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
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
    *返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列。
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
    *取两个set集合的交集保存到指定的destkey中，结果集中某个成员的 score 值是所有给定集下该成员 score 值之和.
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
    *取两个set集合的并集并保存到指定的destKey中
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
