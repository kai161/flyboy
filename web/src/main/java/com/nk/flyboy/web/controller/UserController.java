package com.nk.flyboy.web.controller;

import com.nk.flyboy.core.action.user.UserInfoAction;
import com.nk.flyboy.core.service.RemoteConfig;
import com.nk.flyboy.core.service.redis.queue.Product;
import com.nk.flyboy.core.util.IDGenerator;
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
    private Product product;
    @Resource
    private RemoteConfig remoteConfig;


    @SqlFilter
    @RequestMapping(value = "/userinfo")
    public String userInfo(ModelMap model){

        logger.info("logger for controller");

        List<Member> list=userInfoAction.execute();
        model.addAttribute("list",list);


        product.setKey();

        return "userInfo";

    }

    @RequestMapping(value = "/id")
    @ResponseBody
    public Long getId(){
       return IDGenerator.generateId();
    }

    @ResponseBody
    @RequestMapping(value = "/remoteconf")
    public String getRemoteConf(){
        return remoteConfig.toString();
    }
}
