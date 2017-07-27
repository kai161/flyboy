package com.nk.flyboy.dao;

import com.nk.flyboy.dao.mapper.PrepaymentRecordMapper;
import com.nk.flyboy.model.PrepaymentRecord;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created on 2017/7/24.
 */
@Repository
public class PrepaymentRecordDao {

    @Resource
    private PrepaymentRecordMapper prepaymentRecordMapper;

    public List<PrepaymentRecord> getPrepaymentList(){
        return prepaymentRecordMapper.getPrepaymentList();
    }
}
