package com.nk.flyboy.core.design_pattern.template;

/**
 * Created on 2017/3/30.
 */
public abstract class Game {

    abstract void initialize();
    abstract void startPlay();
    abstract void endPlay();

    public final void play(){
        initialize();

        startPlay();

        endPlay();
    }
}
