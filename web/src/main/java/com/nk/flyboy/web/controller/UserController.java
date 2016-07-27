package com.nk.flyboy.web.controller;

import com.nk.flyboy.core.action.user.TestAction;
import com.nk.flyboy.core.action.user.UserInfoAction;
import com.nk.flyboy.model.Member;
import com.nk.flyboy.web.annotation.SqlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by cheris on 2016/5/24.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private Logger logger= LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserInfoAction userInfoAction;
    @Resource
    private TestAction testAction;

    @SqlFilter
    @RequestMapping(value = "/userinfo")
    public String userInfo(ModelMap model){

        logger.info("logger for controller");

/*        model.addAttribute("name", "xiaoming");
        model.addAttribute("age", 18);
        model.addAttribute("addr", "beijin china");*/

        List<Member> list=userInfoAction.execute();
        model.addAttribute("list",list);
        return "userInfo";
    }

    @ResponseBody
    @RequestMapping(value = "test")
    public String test(){

        testAction.execute();

        return "ok";
    }

}
