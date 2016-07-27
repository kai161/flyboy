package com.nk.flyboy.core.action;

import com.nk.flyboy.core.action.user.UserInfoAction;
import com.nk.flyboy.core.service.UserInfoService;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by cheris on 2016/5/26.
 */
@RunWith(JMockit.class)
public class UserInfoTest {

    @Tested
    private UserInfoAction userInfoAction;

    @Injectable
    private UserInfoService userInfoService;


    @Test
    public void test(){

    }

}
