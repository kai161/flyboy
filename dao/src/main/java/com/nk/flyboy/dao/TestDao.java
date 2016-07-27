package com.nk.flyboy.dao;

import com.nk.flyboy.dao.mapper.TestMapper;
import com.nk.flyboy.model.Test;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by cheris on 2016/7/26.
 */
@Repository
public class TestDao {

    @Resource
    private TestMapper testMapper;

    public void insert(Test test){
        testMapper.insert(test);
    }
}
