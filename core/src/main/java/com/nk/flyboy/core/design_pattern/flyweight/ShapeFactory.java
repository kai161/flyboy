package com.nk.flyboy.core.design_pattern.flyweight;

import java.util.HashMap;

/**
 * Created on 2017/3/28.
 */
public class ShapeFactory {

    private static final HashMap<String,Shape> circleShapes=new HashMap<>();

    public static Shape getCircle(String color){
        Circle circle=(Circle) circleShapes.get(color);

        if(circle==null){
            circle=new Circle(color);
            circleShapes.put(color,circle);
            System.out.println("create circle of color:"+color);
        }

        return circle;
    }
}
