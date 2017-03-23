package com.nk.flyboy.core.design_pattern.adapter;

/**
 * Created on 2017/3/23.
 */
public class Mp4Player implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {

    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("play mp4 "+fileName);
    }
}
