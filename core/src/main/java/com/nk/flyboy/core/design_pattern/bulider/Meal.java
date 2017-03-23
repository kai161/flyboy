package com.nk.flyboy.core.design_pattern.bulider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/3/22.
 */
public class Meal {

    private List<Item> items=new ArrayList<>();

    public void addItem(Item item){
        items.add(item);
    }

    public float getCost(){
        float cost=0.0f;
        for(Item item:items){
            cost+=item.price();
        }
        return cost;
    }

    public void showItems(){
        for (Item item:items){
            System.out.println("item name "+item.name());
            System.out.println("item price "+item.price());
            System.out.println("item packing "+item.packing().pack());
        }
    }
}
