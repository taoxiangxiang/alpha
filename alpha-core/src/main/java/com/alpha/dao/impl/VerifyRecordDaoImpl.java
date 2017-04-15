package com.alpha.dao.impl;

import com.alpha.dao.VerifyRecordDao;
import com.alpha.domain.VerifyRecordDO;
import com.alpha.query.VerifyRecordQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/6.
 */
@Component("verifyRecordDao")
public class VerifyRecordDaoImpl implements VerifyRecordDao {
    @Override
    public boolean insert(VerifyRecordDO verifyRecordDO) {
        return false;
    }

    @Override
    public List<VerifyRecordDO> query(VerifyRecordQuery verifyRecordQuery) {
        return null;
    }

    @Override
    public int count(VerifyRecordQuery verifyRecordQuery) {
        return 0;
    }
}
