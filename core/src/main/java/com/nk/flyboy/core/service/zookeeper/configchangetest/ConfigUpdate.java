package com.nk.flyboy.core.service.zookeeper.configchangetest;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by cheris on 2016/9/5.
 */
public class ConfigUpdate {

    public static final String PATH="/config";

    public ActiveKeyValueStore store;

    public Random random=new Random();

    public ConfigUpdate(String host) throws IOException, InterruptedException {
        store=new ActiveKeyValueStore();
        store.connect(host);
    }

    public void run() throws KeeperException, InterruptedException {
        while (true){
            String value=random.nextInt(100)+"";
            store.write(PATH,value);
            System.out.printf("set %s to %s \n", PATH, value);

            TimeUnit.SECONDS.sleep(random.nextInt(100));
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ConfigUpdate configUpdate=new ConfigUpdate("192.168.177.130:2191");
        configUpdate.run();
    }

}
