package com.nk.flyboy.core.design_pattern.observer;

/**
 * Created on 2017/3/30.
 *
 * 观察者模式
 *
 * 优点：
 *  1、观察者和被观察者是抽象耦合的。 2、建立一套触发机制。
 * 缺点：
 *  1、如果一个被观察者对象有很多的直接和间接的观察者的话，将所有的观察者都通知到会花费很多时间。
 *  2、如果在观察者和观察目标之间有循环依赖的话，观察目标会触发它们之间进行循环调用，可能导致系统崩溃。
 *  3、观察者模式没有相应的机制让观察者知道所观察的目标对象是怎么发生变化的，而仅仅只是知道观察目标发生了变化。
 */
public class Test {

    public static void main(String[] args) {
        Subject subject=new Subject();

        new BinaryObserver(subject);
        new OctalObserver(subject);
        new HexaObserver(subject);

        System.out.println("first state change: 15");
        subject.setState(15);

        System.out.println("second state change 10");
        subject.setState(10);
    }
}
