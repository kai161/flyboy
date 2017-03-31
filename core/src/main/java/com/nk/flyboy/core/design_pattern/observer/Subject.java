package com.nk.flyboy.core.design_pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/3/30.
 */
public class Subject {

    private List<Observer> observers=new ArrayList<>();

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (Observer observer:observers){
            observer.update();
        }
    }
}
