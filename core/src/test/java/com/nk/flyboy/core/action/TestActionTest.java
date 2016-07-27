package com.nk.flyboy.core.action;

import com.nk.flyboy.core.action.user.TestAction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by cheris on 2016/7/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class TestActionTest extends AbstractTransactionalJUnit4SpringContextTests  {


   /* @BeforeClass
    public static void prepare(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        testAction=applicationContext.getBean("testAction",TestAction.class);
    }*/

    @Resource
    public TestAction testAction;

    @Test
    public void test(){

        //testAction.execute();

    }
}
