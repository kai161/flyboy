package com.nk.flyboy.core.action.user;

import com.nk.flyboy.dao.PrepaymentRecordDao;
import com.nk.flyboy.model.Member;
import com.nk.flyboy.model.PrepaymentRecord;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created on 2017/7/24.
 */
@Component
public class PrepaymentRecordAction {

    @Resource
    private PrepaymentRecordDao prepaymentRecordDao;

    public List<PrepaymentRecord> execute(){

        List<PrepaymentRecord> list=prepaymentRecordDao.getPrepaymentList();

        return list;

    }
}
