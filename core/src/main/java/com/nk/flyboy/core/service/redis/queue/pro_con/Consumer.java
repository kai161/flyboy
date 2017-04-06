package com.nk.flyboy.core.service.redis.queue.pro_con;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by cheris on 2016/8/30.
 */
public class Consumer {

    private Jedis jedis;

    public Consumer(String host,int port){
        this.jedis=new Jedis(host,port);
    }

    /*
     * brpop 返回的list 包含两个元素，第一个是key 第二个是msg
     * 若传递多个key时，brpop会取尽第一个key后再去取第二个key的内容
     * */
    public void start(final String... keys){
        new Thread(new Runnable() {
            public void run() {
                boolean flag=true;
                while (flag){
                    List<String> msg=jedis.brpop(30,keys);
                    if(msg!=null&&msg.size()>0){
                        for(String item :msg){
                            System.out.println("msg :"+item);
                            if(item.equalsIgnoreCase("quit")){
                                jedis.close();
                                flag=false;
                            }
                        }
                    }
                }
            }
        }).start();


    }
}
