package com.nk.flyboy.core.design_pattern.bridge;

/**
 * Created on 2017/3/23.
 */
public class Circle extends Shape {

    private int x, y, radius;

    public Circle(int x,int y,int radius,DrawAPI drawAPI){
        super(drawAPI);
        this.x=x;
        this.y=y;
        this.radius=radius;
    }
    @Override
    public void draw() {
        drawAPI.drawCircle(radius,x,y);
    }
}
