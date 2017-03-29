package com.nk.flyboy.core.design_pattern.chainofresponsibility;

/**
 * Created on 2017/3/29.
 */
public class ConsoleLogger extends AbstractLogger {

    public ConsoleLogger(int level){
        this.level=level;
    }

    @Override
    public void write(String message) {
        System.out.println("Console logger message:"+message);
    }
}
