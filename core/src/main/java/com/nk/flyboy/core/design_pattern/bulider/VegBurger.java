package com.nk.flyboy.core.design_pattern.bulider;

/**
 * Created on 2017/3/22.
 */
public class VegBurger extends Burger {
    @Override
    public String name() {
        System.out.println("VegBurger");
        return "VegBurger";
    }

    @Override
    public float price() {
        return 6;
    }
}
