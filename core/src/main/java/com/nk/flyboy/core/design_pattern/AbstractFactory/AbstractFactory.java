package com.nk.flyboy.core.design_pattern.abstractfactory;

/**
 * Created on 2017/3/22.
 */
public abstract class AbstractFactory {
    abstract Shape getShape(String shapyType);
    abstract Color getColor(String colorType);
}
