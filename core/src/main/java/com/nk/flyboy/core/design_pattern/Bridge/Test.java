package com.nk.flyboy.core.design_pattern.Bridge;

/**
 * Created on 2017/3/23.
 *
 * 优点： 1、抽象和实现的分离。 2、优秀的扩展能力。 3、实现细节对客户透明。
 * 缺点：桥接模式的引入会增加系统的理解与设计难度，由于聚合关联关系建立在抽象层，要求开发者针对抽象进行设计与编程。
 */
public class Test {
    public static void main(String[] args) {
        Circle redCircle=new Circle(10,10,10,new RedCircle());
        Circle greenCircle=new Circle(10,10,10,new GreenCircle());

        redCircle.draw();
        greenCircle.draw();

    }
}
