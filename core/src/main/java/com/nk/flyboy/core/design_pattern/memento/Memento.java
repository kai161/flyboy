package com.nk.flyboy.core.design_pattern.memento;

/**
 * Created on 2017/3/29.
 */
public class Memento {
    public String getState() {
        return state;
    }

    private String state;

    public Memento(String state){
        this.state=state;
    }
}
