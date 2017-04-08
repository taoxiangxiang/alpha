package com.alpha.manager;

import com.alpha.domain.VerifyRecordDO;
import com.alpha.query.VerifyRecordQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/6.
 */
public interface VerifyRecordManager {

    boolean insert(VerifyRecordDO verifyRecordDO);

    List<VerifyRecordDO> query(VerifyRecordQuery verifyRecordQuery);
}
