package com.nk.flyboy.core.design_pattern.visitor;

/**
 * Created on 2017/3/30.
 */
public interface ComputerPart {
    void accept(ComputerPartVisitor computerPartVisitor);
}
