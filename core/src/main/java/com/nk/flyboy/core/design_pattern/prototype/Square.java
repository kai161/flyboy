package com.nk.flyboy.core.design_pattern.prototype;

/**
 * Created on 2017/3/31.
 */
public class Square extends Shape {

    public Square(){
        type="square";
    }

    @Override
    void draw() {
        System.out.println("inside square::draw() method");
    }
}
