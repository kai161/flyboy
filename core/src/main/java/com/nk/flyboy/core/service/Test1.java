package com.nk.flyboy.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cheris on 2016/7/22.
 */
@Service
public class Test1 {

    @Transactional(propagation = Propagation.NESTED)
    public void test1(){

        throw  new RuntimeException("ces");
    }
}
