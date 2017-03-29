package com.nk.flyboy.core.design_pattern.interpreter;

/**
 * Created on 2017/3/29.
 */
public class TerminalExpression implements Expression {

    private String data;

    public TerminalExpression(String data){
        this.data=data;
    }

    @Override
    public boolean interpret(String context) {
        if(context.contains(data)){
            return true;
        }

        return false;
    }
}
