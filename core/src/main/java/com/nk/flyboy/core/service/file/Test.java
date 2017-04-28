package com.nk.flyboy.core.service.file;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created on 2017/4/28.
 */
public class Test {
    public static void main(String[] args) {
        String testPath="/Users/kai/logs/flyboylogs";

        DirWatch dirWatch=new DirWatch(testPath);

        try {
            dirWatch.watch(new WatchHandle() {
                @Override
                public void handle() {
                    System.out.println("the file is change"+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
