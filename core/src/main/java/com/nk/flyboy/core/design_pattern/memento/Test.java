package com.nk.flyboy.core.design_pattern.memento;

/**
 * Created on 2017/3/29.
 *
 * 备忘录模式
 *
 * 优点：
 *  1、给用户提供了一种可以恢复状态的机制，可以使用户能够比较方便地回到某个历史的状态。 2、实现了信息的封装，使得用户不需要关心状态的保存细节。
 * 缺点：
 *  消耗资源。如果类的成员变量过多，势必会占用比较大的资源，而且每一次保存都会消耗一定的内存。
 */
public class Test {
    public static void main(String[] args) {
        Originator originator=new Originator();
        CareTaker careTaker=new CareTaker();

        originator.setState("step #1");
        originator.setState("step #2");

        careTaker.add(originator.saveStateToMemento());

        originator.setState("step #3");
        careTaker.add(originator.saveStateToMemento());

        originator.setState("step #4");

        System.out.println("current step:"+originator.getState());

        originator.getStateFromMemento(careTaker.get(0));
        System.out.println("first save step:"+originator.getState());

        originator.getStateFromMemento(careTaker.get(1));
        System.out.println("second save step:"+originator.getState());
    }
}
