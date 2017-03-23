package com.nk.flyboy.core.design_pattern.abstractfactory;

/**
 * Created on 2017/3/22.
 *
 * 优点
 * 当一个产品族中的多个对象被设计成一起工作时，它能保证客户端始终只使用同一个产品族中的对象
 *
 * 缺点
 * 产品族扩展非常困难，要增加一个系列的某一产品，既要在抽象的 Creator 里加代码，又要在具体的里面加代码。
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
