package com.nk.flyboy.core.design_pattern.command;

/**
 * Created on 2017/3/29.
 */
public class BuyStock implements Order {

    private Stock abcStock;

    public BuyStock(Stock stock){
        this.abcStock=stock;
    }

    @Override
    public void execute() {
        abcStock.buy();
    }
}
