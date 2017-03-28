package com.nk.flyboy.core.design_pattern.facade;

/**
 * Created on 2017/3/28.
 */
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("shape:square");
    }
}
