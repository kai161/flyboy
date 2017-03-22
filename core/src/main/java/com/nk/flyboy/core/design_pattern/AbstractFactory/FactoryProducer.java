package com.nk.flyboy.core.design_pattern.AbstractFactory;

/**
 * Created on 2017/3/22.
 */
public class FactoryProducer {

    public static AbstractFactory getFactory(String choice){
        if(choice==null){
            return null;
        }
        if(choice.equalsIgnoreCase("shape")){
            return new ShapeFactory();
        }
        if(choice.equalsIgnoreCase("color")){
            return new ColorFactory();
        }
        return null;
    }
}
