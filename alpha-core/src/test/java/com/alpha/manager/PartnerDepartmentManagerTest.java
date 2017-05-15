package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DepartmentDO;
import com.alpha.domain.PartnerDepartmentDO;
import com.alpha.query.DepartmentQuery;
import com.alpha.query.PartnerDepartmentQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class PartnerDepartmentManagerTest extends ITestBase {

    @Resource
    private PartnerDepartmentManager partnerDepartmentManager;

    @Test
    public void testInsert() {
        PartnerDepartmentDO partnerDepartmentDO = new PartnerDepartmentDO();
        partnerDepartmentDO.setDepartmentName("1");
        partnerDepartmentDO.setType("2");
        partnerDepartmentDO.setAddress("3");
        partnerDepartmentDO.setTelNumber("4");
        partnerDepartmentDO.setContact("5");
        partnerDepartmentDO.setRemark("6");
        partnerDepartmentDO.setMobilePhone("7");
        partnerDepartmentDO.setStatus(SystemConstant.DEPARTMENT_ON_LINE);
        boolean res = partnerDepartmentManager.insert(partnerDepartmentDO);
        System.out.println("res=" + res);
    }

    @Test
    public void testQuery() {
        PartnerDepartmentQuery partnerDepartmentQuery = new PartnerDepartmentQuery();
        partnerDepartmentQuery.setPage(1);
        partnerDepartmentQuery.setPageSize(10);
        partnerDepartmentQuery.setType("2");
        List<PartnerDepartmentDO> list = partnerDepartmentManager.query(partnerDepartmentQuery);
        System.out.println("res=" + JSON.toJSONString(list));
    }

    @Test
    public void testUpdate() {
        PartnerDepartmentDO partnerDepartmentDO = new PartnerDepartmentDO();
        partnerDepartmentDO.setId(1);
        partnerDepartmentDO.setType("3");
        boolean res = partnerDepartmentManager.update(partnerDepartmentDO);
        System.out.println("res=" + res);
    }
}
