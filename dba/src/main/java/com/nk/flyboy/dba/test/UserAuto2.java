package com.nk.flyboy.dba.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/10/24.
 */
@Component
public class UserAuto2 {

    @Autowired
    public AutoAnnotation autoAnnotation;

    public void userAuto2(){
        autoAnnotation.test("userAuto2");
        System.out.println("this is userAuto2");
    }
}
