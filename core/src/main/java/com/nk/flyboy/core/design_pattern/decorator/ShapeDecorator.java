package com.nk.flyboy.core.design_pattern.decorator;

/**
 * Created on 2017/3/28.
 */
public abstract class ShapeDecorator implements Shape {

    protected Shape decoratorShape;

    public ShapeDecorator(Shape decoratorShape){
        this.decoratorShape=decoratorShape;
    }

    @Override
    public void draw() {
        this.decoratorShape.draw();
    }
}
