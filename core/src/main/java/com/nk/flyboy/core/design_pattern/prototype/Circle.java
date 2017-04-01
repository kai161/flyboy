package com.nk.flyboy.core.design_pattern.prototype;

/**
 * Created on 2017/3/31.
 */
public class Circle extends Shape {

    public Circle(){
        type="circle";
    }
    @Override
    void draw() {
        System.out.println("inside circle::draw() method");
    }
}
