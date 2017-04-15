package com.alpha.manager.impl;

import com.alpha.dao.DepartmentDao;
import com.alpha.domain.DepartmentDO;
import com.alpha.manager.DepartmentManager;
import com.alpha.query.DepartmentQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("departmentManager")
public class DepartmentManagerImpl implements DepartmentManager {

    @Resource
    private DepartmentDao departmentDao;

    @Override
    public boolean insert(DepartmentDO departmentDO) {
        return departmentDao.insert(departmentDO);
    }

    @Override
    public List<DepartmentDO> query(DepartmentQuery departmentQuery) {
        return departmentDao.query(departmentQuery);
    }

    @Override
    public boolean update(DepartmentDO departmentDO) {
        return departmentDao.update(departmentDO);
    }

    @Override
    public int count(DepartmentQuery departmentQuery) {
        return departmentDao.count(departmentQuery);
    }
}
