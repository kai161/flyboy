package com.nk.flyboy.core.util;

import java.util.*;

/**
 * Created by kai on 2016/12/26.
 * ���ؾ���ʵ�ַ���
 */
public class LoadBalance {

    private static Integer pos=0;
    /**
     * ��ѯ��
     * serverWeightMap: string ��ip��Integer �� Ȩ��
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
     * ������ʷ�
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
     * ԭ��ַhash��
     * Դ��ַ��ϣ��˼���ǻ�ȡ�ͻ��˷��ʵ�IP��ֵַ��ͨ����ϣ��������õ�һ����ֵ���ø���ֵ�Է������б�Ĵ�С����ȡģ���㣬�õ��Ľ������Ҫ���ʵķ����������
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
     * ��Ȩ��ѯ��
     * ��ͬ�ķ��������ܻ������ú͵�ǰϵͳ�ĸ��ز�����ͬ��������ǵĿ�ѹ����Ҳ������ͬ�������øߡ����ص͵Ļ������ø��ߵ�Ȩ�أ����䴦���������󣬶������á��߸��صĻ�������������ϵ͵�Ȩ�أ�������ϵͳ����
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
     * ��Ȩ�����
     * ���Ȩ��ѯ�����ƣ���Ȩ�����Ҳ�Ǹ��ݺ�˷�������ͬ�����ú͸�����������ò�ͬ��Ȩ�ء���ͬ���ǣ����ǰ���Ȩ�������ѡ��������ģ�������˳��
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
