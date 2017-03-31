package com.nk.flyboy.core.design_pattern.state;

/**
 * Created on 2017/3/30.
 *
 * 带有某个状态的类
 */
public class Context {

    private State state;

    public Context(){
        state=null;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
