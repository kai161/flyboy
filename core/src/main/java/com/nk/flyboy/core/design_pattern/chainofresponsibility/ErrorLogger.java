package com.nk.flyboy.core.design_pattern.chainofresponsibility;

/**
 * Created on 2017/3/29.
 */
public class ErrorLogger extends AbstractLogger {

    public ErrorLogger(int level){
        this.level=level;
    }

    @Override
    public void write(String message) {
        System.out.println(" Error Logger message: "+message);
    }
}
