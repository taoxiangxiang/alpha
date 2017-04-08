package com.alpha.manager.impl;

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
    private DepartmentManager departmentManager;

    @Override
    public boolean insert(DepartmentDO departmentDO) {
        return departmentManager.insert(departmentDO);
    }

    @Override
    public List<DepartmentDO> query(DepartmentQuery departmentQuery) {
        return departmentManager.query(departmentQuery);
    }

    @Override
    public boolean update(DepartmentDO departmentDO) {
        return departmentManager.update(departmentDO);
    }
}
