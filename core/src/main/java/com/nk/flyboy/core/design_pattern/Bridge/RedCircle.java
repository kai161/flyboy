package com.nk.flyboy.core.design_pattern.Bridge;

/**
 * Created on 2017/3/23.
 */
public class RedCircle implements DrawAPI {
    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("this is red circle radius is"+radius+", x"+x+",y"+y);
    }
}
