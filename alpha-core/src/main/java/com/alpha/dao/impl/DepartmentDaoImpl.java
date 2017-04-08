package com.alpha.dao.impl;

import com.alpha.dao.DepartmentDao;
import com.alpha.domain.DepartmentDO;
import com.alpha.query.DepartmentQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Component("departmentDao")
public class DepartmentDaoImpl implements DepartmentDao {
    @Override
    public boolean insert(DepartmentDO departmentDO) {
        return false;
    }

    @Override
    public List<DepartmentDO> query(DepartmentQuery departmentQuery) {
        return null;
    }

    @Override
    public boolean update(DepartmentDO departmentDO) {
        return false;
    }
}
