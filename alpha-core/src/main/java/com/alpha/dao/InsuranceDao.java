package com.alpha.dao;

import com.alpha.domain.InsuranceDO;
import com.alpha.query.InsuranceQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
public interface InsuranceDao {

    boolean insert(InsuranceDO insuranceDO);

    List<InsuranceDO> query(InsuranceQuery insuranceQuery);

    int count(InsuranceQuery insuranceQuery);

    boolean update(InsuranceDO insuranceDO);
}
