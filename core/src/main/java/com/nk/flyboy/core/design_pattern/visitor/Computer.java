package com.nk.flyboy.core.design_pattern.visitor;

/**
 * Created on 2017/3/30.
 */
public class Computer implements ComputerPart {

    ComputerPart[] parts;

    public Computer(){
        parts=new ComputerPart[]{new Keyboard(),new Monitor(),new Mouse()};
    }
    @Override
    public void accept(ComputerPartVisitor computerPartVisitor) {
        for (ComputerPart computerPart:parts){
            computerPart.accept(computerPartVisitor);
        }

        computerPartVisitor.visit(this);
    }
}
