package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DepartmentDO;
import com.alpha.query.DepartmentQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class DepartmentManagerTest extends ITestBase {

    @Resource
    private DepartmentManager departmentManager;

    @Test
    public void testInsert() {
        DepartmentDO departmentDO = new DepartmentDO();
        departmentDO.setDepartmentName("测试子部门");
        departmentDO.setBelongDepartmentName("父部门");
        departmentDO.setDepartmentContact("测试联系人");
        departmentDO.setDepartmentContactPhone("110001");
        departmentDO.setDepartmentAddress("测试部门地址");
        departmentDO.setStatus(SystemConstant.DEPARTMENT_ON_LINE);
        boolean res = departmentManager.insert(departmentDO);
        System.out.println("res=" + res);
    }

    @Test
    public void testQuery() {
        DepartmentQuery departmentQuery = new DepartmentQuery();
        departmentQuery.setPage(1);
        departmentQuery.setPageSize(10);
        departmentQuery.setDepartmentName(null);
        List<DepartmentDO> list = departmentManager.query(departmentQuery);
        System.out.println("res=" + JSON.toJSONString(list));
    }

    @Test
    public void testUpdate() {
        DepartmentDO departmentDO = new DepartmentDO();
        departmentDO.setId(1);
        departmentDO.setDepartmentName("测试子部门new");
        departmentDO.setBelongDepartmentName("父部门new");
        departmentDO.setDepartmentContact("测试联系人");
        departmentDO.setDepartmentContactPhone("1100012");
        departmentDO.setDepartmentAddress("测试部门地址");
        boolean res = departmentManager.update(departmentDO);
        System.out.println("res=" + res);
    }
}
