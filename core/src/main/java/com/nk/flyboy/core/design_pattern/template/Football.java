package com.nk.flyboy.core.design_pattern.template;

/**
 * Created on 2017/3/30.
 */
public class Football extends Game {
    @Override
    void initialize() {
        System.out.println("football is initialized,start play");
    }

    @Override
    void startPlay() {
        System.out.println("football is started, enjoy the game");
    }

    @Override
    void endPlay() {
        System.out.println("game over");
    }
}
