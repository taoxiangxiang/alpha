package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DepartmentDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.query.DepartmentQuery;
import com.alpha.query.SystemAccountQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class SystemAccountManagerTest extends ITestBase {

    @Resource
    private SystemAccountManager systemAccountManager;

    @Test
    public void testInsert() {
//        DepartmentDO departmentDO = new DepartmentDO();
//        departmentDO.setDepartmentName("测试子部门");
//        departmentDO.setBelongDepartmentName("父部门");
//        departmentDO.setDepartmentContact("测试联系人");
//        departmentDO.setDepartmentContactPhone("110001");
//        departmentDO.setDepartmentAddress("测试部门地址");
//        departmentDO.setStatus(SystemConstant.DEPARTMENT_ON_LINE);
//        boolean res = departmentManager.insert(departmentDO);
//        System.out.println("res=" + res);
    }

    @Test
    public void testQuery() {
        SystemAccountQuery systemAccountQuery = new SystemAccountQuery();
        systemAccountQuery.setPage(1);
        systemAccountQuery.setPageSize(10);
        List<SystemAccountDO> list = systemAccountManager.query(systemAccountQuery);
        System.out.println("res=" + JSON.toJSONString(list));
    }


    @Test
    public void testUpdate() {
        SystemAccountDO systemAccountDO = new SystemAccountDO();
        systemAccountDO.setId(13);
        systemAccountDO.setName("管理员");
        systemAccountDO.setSex("男");
        boolean res = systemAccountManager.update(systemAccountDO);
        System.out.println("res=" + res);
    }
}
