package com.nk.flyboy.core.design_pattern.chainofresponsibility;

/**
 * Created on 2017/3/29.
 */
public abstract class AbstractLogger {
    public static int INFO=1;
    public static int DEBUG=2;
    public static int ERROR=3;

    protected int level;

    protected AbstractLogger nextLogger;

    public void setNextLogger(AbstractLogger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void logMessage(int level,String message){
        if(this.level<=level){
            write(message);
        }

        if(nextLogger!=null){
            nextLogger.logMessage(level,message);
        }
    }

    abstract public void write(String message);
}
