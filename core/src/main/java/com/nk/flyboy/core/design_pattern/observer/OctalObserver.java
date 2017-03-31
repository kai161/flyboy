package com.nk.flyboy.core.design_pattern.observer;

/**
 * Created on 2017/3/30.
 */
public class OctalObserver extends Observer {

    public OctalObserver(Subject subject){
        this.subject=subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("OctalObserver :"+subject.getState());
    }
}
