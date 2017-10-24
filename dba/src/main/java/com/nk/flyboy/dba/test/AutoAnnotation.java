package com.nk.flyboy.dba.test;

import org.springframework.stereotype.Component;

/**
 * Created on 2017/10/24.
 */
@Component
public class AutoAnnotation {

    public String info="before";

    public void  test(String msg){
        System.out.println(info+ " this is test "+ msg);
    }

}
