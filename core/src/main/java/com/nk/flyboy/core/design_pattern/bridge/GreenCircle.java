package com.nk.flyboy.core.design_pattern.bridge;

/**
 * Created on 2017/3/23.
 */
public class GreenCircle implements DrawAPI {
    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("this is green circle,radius"+radius+",x"+x+",y"+y);
    }
}
