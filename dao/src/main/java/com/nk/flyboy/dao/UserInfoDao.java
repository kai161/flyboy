package com.nk.flyboy.dao;

import com.nk.flyboy.dao.mapper.UserInfoMapper;
import com.nk.flyboy.jdbc.BaseMapper;
import com.nk.flyboy.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheris on 2016/5/25.
 */
@Repository
public class UserInfoDao {

    private Logger logger= LoggerFactory.getLogger(UserInfoDao.class);

    @Resource
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;



    @Resource
    private UserInfoMapper userInfoMapper;

    public UserInfoDao(){

    }

    /**
     * Cacheable 中value表示数据将缓存在哪个Cache上,key为SpEL表达式语言
     *
     */
    @Cacheable(value = "myCache",key = "'memberList'")
    public List<Member> getMemberList(){

        logger.debug("begin to select from DB ...");

       List<Member> list=new ArrayList<Member>();

 /*        jdbcTemplate=new JdbcTemplate(dataSource);
        String sql="select * from member";
        //Member member=new Member();
        list=jdbcTemplate.query(sql,new BaseMapper<Member>(new Member()));*/




        list=userInfoMapper.getMemberList();


        return list;
    }

    public void insert(Member member){
        userInfoMapper.insert(member);
    }

}
