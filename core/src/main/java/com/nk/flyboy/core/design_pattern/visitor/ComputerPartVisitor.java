package com.nk.flyboy.core.design_pattern.visitor;

/**
 * Created on 2017/3/30.
 */
public interface ComputerPartVisitor {
    void visit(Keyboard keyboard);
    void visit(Monitor monitor);
    void visit(Mouse mouse);
    void visit(Computer computer);
}
