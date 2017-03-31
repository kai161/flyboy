package com.nk.flyboy.core.design_pattern.visitor;

/**
 * Created on 2017/3/30.
 */
public class Keyboard implements ComputerPart {
    @Override
    public void accept(ComputerPartVisitor computerPartVisitor) {
        computerPartVisitor.visit(this);
    }
}
