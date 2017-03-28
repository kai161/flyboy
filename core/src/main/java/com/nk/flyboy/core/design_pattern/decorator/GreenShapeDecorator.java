package com.nk.flyboy.core.design_pattern.decorator;

/**
 * Created on 2017/3/28.
 */
public class GreenShapeDecorator extends ShapeDecorator {
    public GreenShapeDecorator(Shape decoratorShape) {
        super(decoratorShape);
    }

    @Override
    public void draw(){
        this.decoratorShape.draw();
        setGreenBorder(decoratorShape);
    }

    public void setGreenBorder(Shape decoratorShape){
        System.out.println("border color: green");
    }
}
