package com.nk.flyboy.core.service.loghelper;

import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * Created on 2017/4/18.
 */
public class LogManager extends Thread {

    private static volatile LogManager logManager;

    public static boolean isRuning=true;

    public static final String DIR="/users/kai/logs/";

    public HashMap<String,LogFileItem> logFileItems=new HashMap<>();

    private LogManager(){

    }

    public static LogManager getLogManager(){
        if(logManager==null){
            synchronized (LogManager.class){
                if(logManager==null){
                    logManager=new LogManager();
                    logManager.setName("logManager");
                    logManager.start();
                }
            }
        }

        return logManager;
    }

    public void addLogInfo(String fileName,String logInfo){
        LogFileItem item=logFileItems.get(fileName);
        if(item==null){
            synchronized (this){
                item=logFileItems.get(fileName);
                if(item==null){
                    item=new LogFileItem();
                    item.fileName=fileName;
                    logFileItems.put(fileName,item);
                }
            }
        }

        String s="["+fileName+"]-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss SSS"))+logInfo+"\n";
        if(item.currentBuff.equals("A")){
            item.buffA.add(s);
        }else {
            item.buffB.add(s);
        }

    }

    public void createFile(LogFileItem logFileItem){

        if(logFileItem==null){
            return;
        }

        if(!StringUtils.isEmpty(logFileItem.filePath)){
            if(logFileItem.currentSize>1000){
                String newFilePath=DIR+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HHmmss SSS"))+"_"+logFileItem.fileName+".log";
                File newFile=new File(newFilePath);

                File oldFile=new File(logFileItem.filePath);
                boolean flag=oldFile.renameTo(newFile);

                System.out.println("copy:"+(flag?"success":"fail"));
                logFileItem.currentSize=0;
            }
        }else {
            String filePath=DIR+logFileItem.fileName;
            File file=new File(filePath);
            if(!file.exists()){
                try {
                    file.createNewFile();
                    logFileItem.filePath=filePath;
                }catch (Exception e){

                }
            }else {
                logFileItem.filePath=filePath;
            }
        }
    }

    public void writeToFile(LogFileItem logFileItem){

        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=new FileOutputStream(logFileItem.filePath,true);

            List<String> buff=null;
            if(logFileItem.currentBuff.equals("A")){
                buff=logFileItem.buffB;
            }else {
                buff=logFileItem.buffA;
            }
            for(int i=0;i<buff.size();i++){
                String log=buff.get(i);
                logFileItem.currentSize+=log.length();
                fileOutputStream.write(log.getBytes());
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            buff.clear();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void flush(){
        if(logFileItems.size()>0){
            Set<Map.Entry<String,LogFileItem>> entries=logFileItems.entrySet();
            /*for(Map.Entry<String,LogFileItem> itemEntry:entries){
                createFile(itemEntry.getValue());
                writeToFile(itemEntry.getValue());
            }*/

            logFileItems.forEach(new BiConsumer<String, LogFileItem>() {
                @Override
                public void accept(String s, LogFileItem logFileItem) {
                    logFileItem.currentBuff=logFileItem.currentBuff.equals("A")?"B":"A";
                    createFile(logFileItem);
                    writeToFile(logFileItem);
                }
            });
        }
    }

    @Override
    public void run(){
        while (isRuning){
            try {
                flush();
                Thread.sleep(100);
            }catch (Exception ex){
                System.out.println(ex.getMessage());;
            }
        }
    }

    public void close(){
        isRuning=false;
        System.out.println("logManager is close");
    }

}
