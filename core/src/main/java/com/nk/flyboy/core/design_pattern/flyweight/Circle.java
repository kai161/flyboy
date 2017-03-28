package com.nk.flyboy.core.design_pattern.flyweight;

/**
 * Created on 2017/3/28.
 */
public class Circle implements Shape {

    private String color;

    private int x,y,radius;

    public Circle(String color){
        this.color=color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("Circle: color-"+color+",x-"+x+",y-"+y+",radius-"+radius);
    }
}
