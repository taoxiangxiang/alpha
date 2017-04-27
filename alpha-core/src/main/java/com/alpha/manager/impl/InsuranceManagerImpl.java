package com.alpha.manager.impl;

import com.alpha.dao.InsuranceDao;
import com.alpha.domain.InsuranceDO;
import com.alpha.manager.InsuranceManager;
import com.alpha.query.InsuranceQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Component("insuranceManager")
public class InsuranceManagerImpl implements InsuranceManager {

    @Resource
    private InsuranceDao insuranceDao;

    @Override
    public boolean insert(InsuranceDO insuranceDO) {
        return insuranceDao.insert(insuranceDO);
    }

    @Override
    public List<InsuranceDO> query(InsuranceQuery insuranceQuery) {
        return insuranceDao.query(insuranceQuery);
    }

    @Override
    public int count(InsuranceQuery insuranceQuery) {
        return insuranceDao.count(insuranceQuery);
    }

    @Override
    public boolean update(InsuranceDO insuranceDO) {
        return insuranceDao.update(insuranceDO);
    }
}
