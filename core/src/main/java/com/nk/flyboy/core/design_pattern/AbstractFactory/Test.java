package com.nk.flyboy.core.design_pattern.AbstractFactory;

/**
 * Created on 2017/3/22.
 */
public class Test {

    public static void main(String[] args) {
        AbstractFactory shapeFactory=FactoryProducer.getFactory("shape");
        Shape shape=shapeFactory.getShape("Circle");
        shape.draw();

        AbstractFactory colorFactory=FactoryProducer.getFactory("Color");
        Color color=colorFactory.getColor("red");
        color.fill();
    }
}
