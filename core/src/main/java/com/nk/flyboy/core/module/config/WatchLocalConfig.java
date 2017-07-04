package com.nk.flyboy.core.module.config;

import com.nk.flyboy.core.module.SpringApplicationContextUtil;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.*;

/**
 * Created on 2017/7/4.
 */
public class WatchLocalConfig {

    private WatchService watchService;

    private String filename;

    public WatchLocalConfig(Resource resource){
        try {
            watchService= FileSystems.getDefault().newWatchService();
            String filePath=resource.getFile().getAbsolutePath();
            filename=resource.getFilename();
            String dirPath=filePath.substring(0,filePath.indexOf(filename));

            System.out.println("WatchLocalConfig:"+filePath);

            Paths.get(dirPath).register(watchService,StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void watch(){
        while (true){
            try {
                System.out.println("WatchLocalConfig working ...");

                WatchKey watchKey=watchService.take();
                if(watchKey!=null){
                  for (WatchEvent watchEvent:watchKey.pollEvents()){
                      Path path=(Path)watchEvent.context();
                      if(watchEvent.kind()== StandardWatchEventKinds.ENTRY_MODIFY&&path.endsWith(filename)){
                          System.out.println("file "+filename+" is change, reading refresh the web ... ");
                          ((ConfigurableApplicationContext)SpringApplicationContextUtil.webApplicationContext).refresh();
                      }
                  }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
