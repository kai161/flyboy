package com.nk.flyboy.core.design_pattern.strategy;

/**
 * Created on 2017/3/22.
 */
public class PrimaryMemberStrategy implements MemberStrategy {
    @Override
    public double calcPrice(Double booksPrice) {
        System.out.println("初级会员享受0.9的折扣");
        return booksPrice*0.9;
    }
}
