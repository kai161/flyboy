package com.nk.flyboy.core.design_pattern.decorator;

/**
 * Created on 2017/3/28.
 *
 * 优点：
 *  装饰类和被装饰类可以独立发展，不会相互耦合，装饰模式是继承的一个替代模式，装饰模式可以动态扩展一个实现类的功能。
 * 缺点：
 *  多层装饰比较复杂。
 */
public class Test {
    public static void main(String[] args) {
        Shape circle=new Circle();

        Shape redCircle=new RedShapeDecorator(circle);

        Shape greenRectangle=new GreenShapeDecorator(new Rectangle());

        circle.draw();

        redCircle.draw();

        greenRectangle.draw();
    }
}
