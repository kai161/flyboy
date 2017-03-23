package com.nk.flyboy.core.design_pattern.bulider;

/**
 * Created on 2017/3/22.
 */
public class ChickBurger extends Burger {
    @Override
    public String name() {
        System.out.println("chickBurger");
        return "ChickBurger";
    }

    @Override
    public float price() {
        return 8.0f;
    }
}
