package com.alpha.manager;

import com.alpha.domain.InsuranceDO;
import com.alpha.query.InsuranceQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
public interface InsuranceManager {
    boolean insert(InsuranceDO insuranceDO);

    List<InsuranceDO> query(InsuranceQuery insuranceQuery);

    int count(InsuranceQuery insuranceQuery);

    boolean update(InsuranceDO insuranceDO);
}
