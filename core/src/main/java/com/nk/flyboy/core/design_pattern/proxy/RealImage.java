package com.nk.flyboy.core.design_pattern.proxy;

/**
 * Created on 2017/3/29.
 */
public class RealImage implements Image {

    private String fileName;

    public RealImage(String fileName){
        this.fileName=fileName;
        loadImage(fileName);

    }

    @Override
    public void display() {
        System.out.println("display image:"+fileName);
    }

    public void loadImage(String fileName){
        System.out.println("load image :"+fileName);
    }
}
