package com.nk.flyboy.core.design_pattern.memento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/3/29.
 */
public class CareTaker {

    private List<Memento> mementos=new ArrayList<>();

    public void add(Memento memento){
        mementos.add(memento);
    }

    public Memento get(int index){
        return mementos.get(index);
    }
}
