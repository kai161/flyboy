package com.nk.flyboy.core.design_pattern.bridge;

/**
 * Created on 2017/3/23.
 */
public abstract class Shape {
    protected DrawAPI drawAPI;

    public Shape(){}
    public Shape(DrawAPI drawAPI){
        this.drawAPI=drawAPI;
    }

    public abstract void draw();
}
