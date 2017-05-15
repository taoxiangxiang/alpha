package com.alpha.manager.impl;

import com.alpha.dao.PartnerDepartmentDao;
import com.alpha.domain.PartnerDepartmentDO;
import com.alpha.manager.PartnerDepartmentManager;
import com.alpha.query.PartnerDepartmentQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/5/9.
 */
@Component("partnerDepartmentManager")
public class PartnerDepartmentManagerImpl implements PartnerDepartmentManager {

    @Resource
    private PartnerDepartmentDao partnerDepartmentDao;

    @Override
    public boolean insert(PartnerDepartmentDO partnerDepartmentDO) {
        return partnerDepartmentDao.insert(partnerDepartmentDO);
    }

    @Override
    public List<PartnerDepartmentDO> query(PartnerDepartmentQuery partnerDepartmentQuery) {
        return partnerDepartmentDao.query(partnerDepartmentQuery);
    }

    @Override
    public int count(PartnerDepartmentQuery partnerDepartmentQuery) {
        return partnerDepartmentDao.count(partnerDepartmentQuery);
    }

    @Override
    public boolean update(PartnerDepartmentDO partnerDepartmentDO) {
        return partnerDepartmentDao.update(partnerDepartmentDO);
    }
}
