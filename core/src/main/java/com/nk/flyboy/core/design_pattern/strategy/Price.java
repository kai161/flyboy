package com.nk.flyboy.core.design_pattern.strategy;

/**
 * Created on 2017/3/22.
 */
public class Price {

    private MemberStrategy memberStrategy;
    public Price(MemberStrategy memberStrategy){
        this.memberStrategy=memberStrategy;
    }

    /**
     * 折扣
     */
    public double quote(double price){
        return this.memberStrategy.calcPrice(price);
    }
}
