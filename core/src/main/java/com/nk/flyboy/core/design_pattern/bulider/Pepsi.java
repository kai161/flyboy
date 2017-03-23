package com.nk.flyboy.core.design_pattern.bulider;

/**
 * Created on 2017/3/22.
 */
public class Pepsi extends ColdDrink {
    @Override
    public String name() {
        System.out.println("Pepsi");
        return "Pepsi";
    }

    @Override
    public float price() {
        return 26f;
    }
}
