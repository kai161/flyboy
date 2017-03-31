package com.nk.flyboy.core.design_pattern.observer;

/**
 * Created on 2017/3/30.
 */
public class BinaryObserver extends Observer {

    public BinaryObserver(Subject subject){
        this.subject=subject;
        this.subject.attach(this);
    }


    @Override
    public void update() {
        System.out.println("BinaryObserver :"+subject.getState());
    }
}
