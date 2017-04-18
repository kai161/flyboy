package com.nk.flyboy.core.service.loghelper;

/**
 * Created on 2017/4/18.
 */
public class Logger {

    private static volatile Logger logger;
    private static LogManager logManager;

    static {
        logManager=LogManager.getLogManager();
    }

    private Logger(){
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            logManager.close();
        }));
    }

    public static Logger getLogger(){
        if(logger==null){
            synchronized (Logger.class){
                if(logger==null){
                    logger=new Logger();
                }
            }
        }
        return logger;
    }

    public void debug(String msg){
        logManager.addLogInfo("debug",msg);
    }

    public void info(String msg){
        logManager.addLogInfo("info",msg);
    }

    public void erro(String msg){
        logManager.addLogInfo("erro",msg);
    }


}
