package com.nk.flyboy.core.design_pattern.decorator;

/**
 * Created on 2017/3/28.
 */
public class RedShapeDecorator extends ShapeDecorator {
    public RedShapeDecorator(Shape decoratorShape) {
        super(decoratorShape);
    }

    @Override
    public void draw(){
        decoratorShape.draw();
        setRedBorder(decoratorShape);
    }

    public void setRedBorder(Shape decoratorShape){
        System.out.println("border color: red");
    }
}
