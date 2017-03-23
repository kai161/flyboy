package com.nk.flyboy.core.design_pattern.abstractfactory;

/**
 * Created on 2017/3/22.
 */
public class ColorFactory extends AbstractFactory {
    @Override
    Shape getShape(String shapyType) {
        return null;
    }

    @Override
    Color getColor(String colorType) {
        if(colorType==null){
            return null;
        }
        if(colorType.equalsIgnoreCase("red")){
            return new Red();
        }
        if(colorType.equalsIgnoreCase("green")){
            return new Green();
        }
        if(colorType.equalsIgnoreCase("blue")){
            return new Blue();
        }
        return null;
    }
}
