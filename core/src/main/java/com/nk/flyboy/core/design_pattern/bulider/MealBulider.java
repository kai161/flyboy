package com.nk.flyboy.core.design_pattern.bulider;

import com.nk.flyboy.core.util.MachineUtil;

/**
 * Created on 2017/3/22.
 */
public class MealBulider {

    public Meal prepareVegMeal(){
        Meal meal=new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new Coke());
        return meal;
    }

    public Meal prepareNonVegMeal(){
        Meal meal=new Meal();
        meal.addItem(new ChickBurger());
        meal.addItem(new Pepsi());
        return meal;
    }

}
