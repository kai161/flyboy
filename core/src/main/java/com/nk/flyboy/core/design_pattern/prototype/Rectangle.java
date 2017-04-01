package com.nk.flyboy.core.design_pattern.prototype;

/**
 * Created on 2017/3/31.
 */
public class Rectangle extends Shape {

    public Rectangle(){
        type="Rectangle";
    }
    @Override
    void draw() {
        System.out.println("Inside Rectangle::draw() method");
    }
}
