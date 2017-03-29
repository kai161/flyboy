package com.nk.flyboy.core.design_pattern.command;

/**
 * Created on 2017/3/29.
 */
public class SellStock implements Order {

    private Stock abcStock;

    public SellStock(Stock stock){
        this.abcStock=stock;
    }

    @Override
    public void execute() {
        abcStock.sell();
    }
}
