package com.nk.flyboy.core.design_pattern.mediator;

/**
 * Created on 2017/3/29.
 *
 * 中介者模式
 * 优点：
 *  1、降低了类的复杂度，将一对多转化成了一对一。 2、各个类之间的解耦。 3、符合迪米特原则。
 * 缺点：
 *  中介者会庞大，变得复杂难以维护。
 */
public class Test {

    public static void main(String[] args) {
        User john=new User("john");
        User robert=new User("robert");

        john.sendMessage("hi,everyone");

        robert.sendMessage("hello john");
    }
}
