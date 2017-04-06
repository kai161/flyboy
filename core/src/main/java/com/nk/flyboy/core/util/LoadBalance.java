package com.nk.flyboy.core.util;

import java.util.*;

/**
 * Created by kai on 2016/12/26.
 * 负载均衡实现方法
 */
public class LoadBalance {

    private static Integer pos=0;
    /**
     * 轮询法
     * serverWeightMap: string 存ip。Integer 存 权重
     */
    public String roundRobin(Map<String,Integer> serverWeightMap){
        Map<String,Integer> serverMap=new HashMap<String, Integer>();
        serverMap.putAll(serverWeightMap);

        Set<String> keySet=serverMap.keySet();
        ArrayList<String> keyList=new ArrayList<String>(keySet);

        String server=null;
        synchronized (pos){
            if(pos>keySet.size()){
                pos=0;
            }
            server=keyList.get(pos);
            pos++;
        }

        return server;
    }

    /**
     * 随机访问法
     */
    public String random(Map<String,Integer> serverWeightMap){
        String server=null;
        Map<String,Integer> serverMap=new HashMap<String, Integer>();
        serverMap.putAll(serverWeightMap);

        Set<String> keySet=serverMap.keySet();
        ArrayList<String> keyList=new ArrayList<String>(keySet);

        Random random=new Random();
        int randomPos=random.nextInt(keyList.size());

        server=keyList.get(randomPos);

        return server;
    }

    /**
     * 原地址hash法
     * 源地址哈希的思想是获取客户端访问的IP地址值，通过哈希函数计算得到一个数值，用该数值对服务器列表的大小进行取模运算，得到的结果便是要访问的服务器的序号
     */
    public String hash(Map<String,Integer> serverWeightMap,String reqIP){
        String server=null;

        Map<String,Integer> serverMap=new HashMap<String, Integer>();
        serverMap.putAll(serverWeightMap);

        Set<String> keySet=serverMap.keySet();
        ArrayList<String> keyList=new ArrayList<String>(keySet);

        int hashCode=reqIP.hashCode();
        int serverPos=hashCode%keyList.size();

        server=keyList.get(serverPos);

        return server;
    }

    /**
     * 加权轮询法
     * 不同的服务器可能机器配置和当前系统的负载并不相同，因此它们的抗压能力也不尽相同，给配置高、负载低的机器配置更高的权重，让其处理更多的请求，而低配置、高负载的机器，则给其分配较低的权重，降低其系统负载
     */
    public String weightRoundRobin(Map<String,Integer> serverWeightMap){
        String server=null;

        Map<String,Integer> serverMap=new HashMap<String, Integer>();
        serverMap.putAll(serverWeightMap);

        Set<String> keySet=serverMap.keySet();
        Iterator<String> iterator=keySet.iterator();
        List<String> serverList=new ArrayList<String>();
        while (iterator.hasNext()){
            String item=iterator.next();
            int weight=serverMap.get(item);
            for(int i=0;i<weight;i++){
                serverList.add(item);
            }
        }

        synchronized (pos){
            if(pos>serverList.size()) pos=0;
            server=serverList.get(pos);
            pos++;
        }

        return server;
    }

    /**
     * 加权随机法
     * 与加权轮询法类似，加权随机法也是根据后端服务器不同的配置和负载情况来配置不同的权重。不同的是，它是按照权重来随机选择服务器的，而不是顺序
     */
    public String weightRandom(Map<String,Integer> serverWeightMap){
        String server=null;

        Map<String,Integer> serverMap=new HashMap<String, Integer>();
        serverMap.putAll(serverWeightMap);

        Set<String> keySet=serverMap.keySet();
        Iterator<String> iterator=keySet.iterator();
        List<String> serverList=new ArrayList<String>();
        while (iterator.hasNext()){
            String item=iterator.next();
            int weight=serverMap.get(item);
            for(int i=0;i<weight;i++){
                serverList.add(item);
            }
        }

        Random random=new Random();
        int randomPos=random.nextInt(serverList.size());
        server=serverList.get(randomPos);

        return server;
    }


}
