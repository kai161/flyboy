package com.nk.flyboy.core.service;

import com.nk.flyboy.dao.TestDao;
import com.nk.flyboy.dao.UserInfoDao;
import com.nk.flyboy.model.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by cheris on 2016/7/22.
 */
@Service
public class Test2 {


    @Resource
    private TestDao testDao;

    @Transactional
    public void test2A(){

        Test test=new Test();

        test.setId("22222");
        test.setName("test2A");

        testDao.insert(test);

        try{
            test2();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void test2(){

        Test test=new Test();

        test.setId("12321");
        test.setName("test1");

        testDao.insert(test);

        throw  new RuntimeException("test2");


    }
}
