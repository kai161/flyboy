package com.nk.flyboy.core.design_pattern.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/3/29.
 */
public class Broker {

    private List<Order> orderList=new ArrayList<>();

    public void takeOrder(Order order){
        orderList.add(order);
    }

    public void placeOrders(){
        for(Order order:orderList){
            order.execute();
        }

        orderList.clear();
    }
}
