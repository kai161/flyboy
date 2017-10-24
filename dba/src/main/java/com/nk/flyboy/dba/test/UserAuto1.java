package com.nk.flyboy.dba.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created on 2017/10/24.
 */
@Component
public class UserAuto1 {

    @Autowired
    private List<AutoAnnotation> autoAnnotations;

    public void userAuto1(){
        autoAnnotations.get(0).test("userAuto1");
        System.out.println("this is userAuto1");
    }
}
