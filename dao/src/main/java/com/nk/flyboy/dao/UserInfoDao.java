package com.nk.flyboy.dao;

import com.nk.flyboy.dao.mapper.UserInfoMapper;
import com.nk.flyboy.jdbc.BaseMapper;
import com.nk.flyboy.model.Member;
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

    @Resource
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Resource
    private UserInfoMapper userInfoMapper;

    public UserInfoDao(){

    }

    public List<Member> getMemberList(){

        List<Member> list=new ArrayList<Member>();
        jdbcTemplate=new JdbcTemplate(dataSource);
        String sql="select * from member";
        //Member member=new Member();
        list=jdbcTemplate.query(sql,new BaseMapper<Member>(new Member()));

        //list=userInfoMapper.getMemberList();


        return list;
    }
}
