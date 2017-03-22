package com.nk.flyboy.core.design_pattern.factory;

/**
 * Created on 2017/3/22.
 */
public class ShapeFactory {

    public static Shape getShape(String shapeType){
        if(shapeType==null){
            return  null;
        }
        if(shapeType.equalsIgnoreCase("Circle")){
            return new Circle();
        }
        if(shapeType.equalsIgnoreCase("Square")){
            return new Square();
        }
        if(shapeType.equalsIgnoreCase("Rectangle")){
            return new Rectangle();
        }
        return null;
    }
}
