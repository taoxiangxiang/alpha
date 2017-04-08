package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.domain.SystemAccountDO;
import com.alpha.query.SystemAccountQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/3/29.
 */
public class UserManagerTest extends ITestBase {

    @Resource
    private SystemAccountManager systemAccountManager;

    @Test
    public void testInsert() {
        SystemAccountDO systemAccountDO = new SystemAccountDO();
        systemAccountDO.setName("管理员");
        systemAccountDO.setNick("admin");
        systemAccountDO.setPassword("1234");
        systemAccountDO.setCitizenId("340223");
        systemAccountDO.setBirth("19901010");
        systemAccountDO.setEthnicGroup("汉族");
        systemAccountDO.setNativePlace("浙江杭州");
        systemAccountDO.setEducation("本科");
        systemAccountDO.setTelNumber("0553-8261069");
        systemAccountDO.setMobilePhone("17891608627");
        systemAccountDO.setMailbox("zhouxingxing@163.com");
        systemAccountDO.setAddress("杭州理工大学");
        systemAccountDO.setHireDate("2007-10-01");
        systemAccountDO.setPosition("系统职位");
        systemAccountDO.setDepartment("系统部门");
        systemAccountDO.setAuthType("ALL");
        systemAccountDO.setPicUrl("picUrl");
        systemAccountDO.setAttribute("attribute");
        System.out.println(systemAccountManager.insert(systemAccountDO));
    }

    @Test
    public void testQuery() {
        SystemAccountQuery systemAccountQuery = new SystemAccountQuery();
        systemAccountQuery.setName("admin");
        List<SystemAccountDO> list = systemAccountManager.query(systemAccountQuery);
        System.out.println("list=" + JSON.toJSONString(list));
    }
}
