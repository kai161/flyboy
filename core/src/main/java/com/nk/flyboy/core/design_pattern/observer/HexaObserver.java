package com.nk.flyboy.core.design_pattern.observer;

/**
 * Created on 2017/3/30.
 */
public class HexaObserver extends Observer {

    public HexaObserver(Subject subject){
        this.subject=subject;
        this.subject.attach(this);
    }
    @Override
    public void update() {
        System.out.println("HexaObserver :"+subject.getState());
    }
}
