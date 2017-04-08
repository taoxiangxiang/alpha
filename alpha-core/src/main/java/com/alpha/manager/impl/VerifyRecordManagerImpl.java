package com.alpha.manager.impl;

import com.alpha.dao.VerifyRecordDao;
import com.alpha.domain.VerifyRecordDO;
import com.alpha.manager.VerifyRecordManager;
import com.alpha.query.VerifyRecordQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/6.
 */
@Component("verifyRecordManager")
public class VerifyRecordManagerImpl implements VerifyRecordManager {

    @Resource
    private VerifyRecordDao verifyRecordDao;

    @Override
    public boolean insert(VerifyRecordDO verifyRecordDO) {
        return verifyRecordDao.insert(verifyRecordDO);
    }

    @Override
    public List<VerifyRecordDO> query(VerifyRecordQuery verifyRecordQuery) {
        return verifyRecordDao.query(verifyRecordQuery);
    }
}
