package com.nk.flyboy.core.design_pattern.command;

/**
 * Created on 2017/3/29.
 */
public class Stock {

    private String name="ABC";
    private int quantity=10;


    public void buy(){
        System.out.println(" stock [name :"+name+", quantity :"+quantity+"] buy");
    }

    public void sell(){
        System.out.println(" stock [name :"+name+", quantity :"+quantity+"] sell");
    }

}
