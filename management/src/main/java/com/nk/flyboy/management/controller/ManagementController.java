package com.nk.flyboy.management.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by kai on 2017/1/11.
 */
@Controller
public class ManagementController {

    @RequestMapping("/welcome")
    @RequiresRoles("admin")
    @ResponseBody
    public String welcome(){

        Subject subject= SecurityUtils.getSubject();

        Session session=subject.getSession();

        boolean isAuth=subject.isAuthenticated();


        return "welcome";
    }
}
