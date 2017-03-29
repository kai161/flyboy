package com.nk.flyboy.core.design_pattern.proxy;

/**
 * Created on 2017/3/29.
 */
public class ProxyImage implements Image {

    private String fileName;
    private RealImage realImage;

    public ProxyImage(String fileName){
        this.fileName=fileName;
    }

    @Override
    public void display() {
        if(realImage==null){
           realImage=new RealImage(fileName);
        }

        realImage.display();
    }
}
