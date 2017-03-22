package com.nk.flyboy.core.design_pattern.AbstractFactory;

/**
 * Created on 2017/3/22.
 */
public class ShapeFactory extends AbstractFactory {
    @Override
    Shape getShape(String shapyType) {
        if(shapyType==null){
            return null;
        }
        if(shapyType.equalsIgnoreCase("Circle")){
            return new Circle();
        }
        if(shapyType.equalsIgnoreCase("Square")){
            return new Square();
        }
        if(shapyType.equalsIgnoreCase("Rectangle")){
            return new Rectangle();
        }
        return null;
    }

    @Override
    Color getColor(String colorType) {
        return null;
    }
}
