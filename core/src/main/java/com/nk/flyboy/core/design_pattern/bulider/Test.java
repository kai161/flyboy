package com.nk.flyboy.core.design_pattern.bulider;

/**
 * Created on 2017/3/22.
 */
public class Test {

    public static void main(String[] args) {
        MealBulider mealBulider=new MealBulider();

        Meal meal=mealBulider.prepareVegMeal();
        meal.showItems();
        System.out.println("meal price"+meal.getCost());

        Meal nonMeal=mealBulider.prepareNonVegMeal();
        nonMeal.showItems();
        System.out.println("nonmeal price"+nonMeal.getCost());
    }
}
