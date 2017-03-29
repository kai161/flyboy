package com.nk.flyboy.core.design_pattern.command;

/**
 * Created on 2017/3/29.
 *
 *
 * 优点：
 *    1、降低了系统耦合度。 2、新的命令可以很容易添加到系统中去。
 * 缺点：
 *    使用命令模式可能会导致某些系统有过多的具体命令类。
 * 使用场景：
 *    认为是命令的地方都可以使用命令模式，比如： 1、GUI 中每一个按钮都是一条命令。 2、模拟 CMD。
 */
public class Test {

    public static void main(String[] args) {
        Stock abcStock=new Stock();

        BuyStock buyStock=new BuyStock(abcStock);
        SellStock sellStock=new SellStock(abcStock);

        Broker broker=new Broker();
        broker.takeOrder(buyStock);
        broker.takeOrder(sellStock);

        broker.placeOrders();

    }
}
