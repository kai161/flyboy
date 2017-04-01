package com.nk.flyboy.core.design_pattern.prototype;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created on 2017/3/31.
 */
public class ShapeCache {
    private static Hashtable<String,Shape> shapeMaps=new Hashtable<>();

    public static Shape getShape(String shapeId){
        return (Shape) shapeMaps.get(shapeId).clone();
    }

    public static void loadCache(){
        Circle circle=new Circle();
        circle.setId("1");
        shapeMaps.put(circle.getId(),circle);

        Rectangle rectangle=new Rectangle();
        rectangle.setId("2");
        shapeMaps.put(rectangle.getId(),rectangle);

        Square square=new Square();
        square.setId("3");
        shapeMaps.put(square.getId(),square);
    }
}
