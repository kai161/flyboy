package com.nk.flyboy.core.design_pattern.strategy;

/**
 * Created on 2017/3/22.
 *
 * 实现一个会员折扣策略模式
 */
public interface MemberStrategy {

    double calcPrice(Double booksPrice);
}
