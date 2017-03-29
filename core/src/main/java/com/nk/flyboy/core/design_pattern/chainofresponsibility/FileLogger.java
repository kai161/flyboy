package com.nk.flyboy.core.design_pattern.chainofresponsibility;

/**
 * Created on 2017/3/29.
 */
public class FileLogger extends AbstractLogger {

    public FileLogger(int level){
        this.level=level;
    }

    @Override
    public void write(String message) {
        System.out.println("File Logger message:"+message);
    }
}
