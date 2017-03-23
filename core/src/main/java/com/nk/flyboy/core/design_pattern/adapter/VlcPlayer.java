package com.nk.flyboy.core.design_pattern.adapter;

/**
 * Created on 2017/3/23.
 */
public class VlcPlayer implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        System.out.println("play Vlc" + fileName);
    }

    @Override
    public void playMp4(String fileName) {

    }
}
