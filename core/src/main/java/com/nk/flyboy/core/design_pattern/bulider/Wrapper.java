package com.nk.flyboy.core.design_pattern.bulider;

/**
 * Created on 2017/3/22.
 */
public class Wrapper implements Packing {
    @Override
    public String pack() {
        System.out.println("wrapper");
        return "wrapper";
    }
}
