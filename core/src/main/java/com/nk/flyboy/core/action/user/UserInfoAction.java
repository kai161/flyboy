package com.nk.flyboy.core.action.user;

import com.nk.flyboy.core.service.UserInfoService;
import com.nk.flyboy.dao.UserInfoDao;
import com.nk.flyboy.model.Member;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheris on 2016/5/25.
 */
@Component
public class UserInfoAction {

    @Resource
    private UserInfoService userInfoService;

    public List<Member> execute(){

        List<Member> list=userInfoService.getMemberList();

        return list;

    }

}
