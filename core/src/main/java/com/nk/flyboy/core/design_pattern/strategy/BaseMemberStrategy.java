package com.nk.flyboy.core.design_pattern.strategy;

/**
 * Created on 2017/3/22.
 */
public class BaseMemberStrategy implements MemberStrategy {

    @Override
    public double calcPrice(Double booksPrice) {
        System.out.println("普通会员没有折扣");
        return booksPrice;
    }
}
