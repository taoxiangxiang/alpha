package com.alpha.dao;

import com.alpha.domain.VerifyRecordDO;
import com.alpha.query.VerifyRecordQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/6.
 */
public interface VerifyRecordDao {

    boolean insert(VerifyRecordDO verifyRecordDO);

    List<VerifyRecordDO> query(VerifyRecordQuery verifyRecordQuery);
}
