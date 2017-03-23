package com.nk.flyboy.core.design_pattern.adapter;

/**
 * Created on 2017/3/23.
 */
public class AudioPlayer implements MediaPlayer {

    @Override
    public void play(String audioType, String fileName) {
        if(audioType.equalsIgnoreCase("mp3")){
            System.out.println("play mp3");
        }else if(audioType.equalsIgnoreCase("vlc")||audioType.equalsIgnoreCase("mp4")){
            MediaAdapter mediaAdapter=new MediaAdapter(audioType);
            mediaAdapter.play(audioType,fileName);
        }else {
            System.out.println("not supported audioType");
        }
    }
}
