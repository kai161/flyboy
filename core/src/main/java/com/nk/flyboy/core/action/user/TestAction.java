package com.nk.flyboy.core.action.user;

import com.nk.flyboy.core.service.Test1;
import com.nk.flyboy.core.service.Test2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by cheris on 2016/7/26.
 */
@Component
public class TestAction {

    @Resource
    private Test1 test1;

    @Resource
    private Test2 test2;

    @Transactional
    public void execute(){

        test2.test2A();


        //test1.test1();
        try{
            test1.test1();
        }catch (Exception ex){

            System.out.println(ex.getMessage());
        }







    }
}
