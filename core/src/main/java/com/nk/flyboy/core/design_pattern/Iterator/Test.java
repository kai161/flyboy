package com.nk.flyboy.core.design_pattern.Iterator;

/**
 * Created on 2017/3/29.
 */
public class Test {

    public static void main(String[] args) {
        NameRepository nameRepository=new NameRepository();
        for (Iterator iterator=nameRepository.getIterator();iterator.hasNext();){
            String name=(String) iterator.next();
            System.out.println("name :"+name);
        }
    }
}
