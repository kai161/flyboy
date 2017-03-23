package com.nk.flyboy.core.design_pattern.bulider;

/**
 * Created on 2017/3/22.
 */
public class Coke extends ColdDrink {
    @Override
    public String name() {
        System.out.println("Coke");
        return "Coke";
    }

    @Override
    public float price() {
        return 30.0f;
    }
}
