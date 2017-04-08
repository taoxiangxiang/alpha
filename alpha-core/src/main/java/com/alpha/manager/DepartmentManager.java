package com.alpha.manager;

import com.alpha.domain.DepartmentDO;
import com.alpha.query.DepartmentQuery;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */

public interface DepartmentManager {

    boolean insert(DepartmentDO departmentDO);

    List<DepartmentDO> query(DepartmentQuery departmentQuery);

    boolean update(DepartmentDO departmentDO);
}
