package com.nk.flyboy.core.service;

import com.nk.flyboy.dao.UserInfoDao;
import com.nk.flyboy.model.Member;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheris on 2016/8/24.
 */
@Service
public class UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;

    public List<Member> getMemberList(){

        List<Member> list=new ArrayList<Member>();
        if(list==null){
            list=userInfoDao.getMemberList();
        }

        return list;
    }

}
