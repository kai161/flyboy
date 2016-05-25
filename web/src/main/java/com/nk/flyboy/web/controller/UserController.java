package com.nk.flyboy.web.controller;

import com.nk.flyboy.core.action.user.UserInfoAction;
import com.nk.flyboy.model.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by cheris on 2016/5/24.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserInfoAction userInfoAction;

    @RequestMapping(value = "/userinfo")
    public String userInfo(ModelMap model){

/*        model.addAttribute("name", "xiaoming");
        model.addAttribute("age", 18);
        model.addAttribute("addr", "beijin china");*/

        List<Member> list=userInfoAction.execute();
        model.addAttribute("list",list);
        return "userInfo";
    }

}
