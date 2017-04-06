package com.nk.flyboy.core.service.zookeeper.configchangetest;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.IOException;

/**
 * Created by cheris on 2016/9/5.
 */
public class ConfigWatcher implements Watcher {


    private ActiveKeyValueStore store;

    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getType()== Event.EventType.NodeDataChanged){

            try {
                displayConfig();
            } catch (KeeperException e) {
                e.printStackTrace();
                System.err.println("KeeperException Exiting...");
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("InterruptedException Exiting...");
                Thread.currentThread().interrupt();
            }
        }
    }

    public ConfigWatcher(String host) throws IOException, InterruptedException {
        store=new ActiveKeyValueStore();
        store.connect(host);
    }

    public void displayConfig() throws KeeperException, InterruptedException {
        String value=store.read(ConfigUpdate.PATH,this);
        System.out.printf("Read %s as %s \n", ConfigUpdate.PATH, value);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ConfigWatcher configWatcher=new ConfigWatcher("192.168.177.130:2191");

        configWatcher.displayConfig();

        Thread.sleep(Long.MAX_VALUE);
    }
}
