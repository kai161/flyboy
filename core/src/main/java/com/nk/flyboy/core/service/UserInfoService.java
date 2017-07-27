package com.nk.flyboy.core.service;

import com.nk.flyboy.dao.PrepaymentRecordDao;
import com.nk.flyboy.dao.UserInfoDao;
import com.nk.flyboy.model.Member;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheris on 2016/5/26.
 */
@Service
public class UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private CacheManager cacheManager;
    @Resource
    private PrepaymentRecordDao prepaymentRecordDao;

    public List<Member> getMemberList(){
        Cache cache=cacheManager.getCache("myCache");
        List<Member> list=cache.get("memberList", new ArrayList<Member>().getClass());
        if(list==null){
            list=userInfoDao.getMemberList();
        }

        return list;
    }
}
