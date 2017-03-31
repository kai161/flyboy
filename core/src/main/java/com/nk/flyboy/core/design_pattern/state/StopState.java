package com.nk.flyboy.core.design_pattern.state;

/**
 * Created on 2017/3/30.
 */
public class StopState implements State {
    @Override
    public void doAction(Context context) {
        System.out.println("play is stop state ");
        context.setState(this);
    }

    public String toString(){
        return "stop state";
    }
}
