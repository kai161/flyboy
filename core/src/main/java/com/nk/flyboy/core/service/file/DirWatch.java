package com.nk.flyboy.core.service.file;

import org.apache.xmlbeans.impl.store.Path;

import java.io.IOException;
import java.nio.file.*;

/**
 * Created on 2017/4/28.
 */
public class DirWatch {

    WatchService watchService;

    public DirWatch(String filePath ){
        try {
            watchService= FileSystems.getDefault().newWatchService();
            Paths.get(filePath).register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void watch(WatchHandle handle) throws InterruptedException {
        while (true){
            WatchKey key=watchService.take();
            if(key!=null){
                for(WatchEvent event: key.pollEvents()){
                    if(event.kind()==StandardWatchEventKinds.ENTRY_MODIFY){
                        handle.handle();
                    }
                }
            }
            key.reset();
        }
    }
}
