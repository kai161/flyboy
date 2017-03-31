package com.nk.flyboy.core.design_pattern.template;

/**
 * Created on 2017/3/30.
 */
public class Cricket extends Game {
    @Override
    void initialize() {
        System.out.println("cricket is initialized,start playing");
    }

    @Override
    void startPlay() {
        System.out.println("criket is started,enjoy the game");
    }

    @Override
    void endPlay() {
        System.out.println("game over");
    }
}
