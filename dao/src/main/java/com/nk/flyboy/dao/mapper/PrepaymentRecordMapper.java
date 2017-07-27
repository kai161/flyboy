package com.nk.flyboy.dao.mapper;

import com.nk.flyboy.model.Member;
import com.nk.flyboy.model.PrepaymentRecord;

import java.util.List;

/**
 * Created on 2017/7/24.
 */
public interface PrepaymentRecordMapper {

    List<PrepaymentRecord> getPrepaymentList();
}
