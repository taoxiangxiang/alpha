package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverDO;
import com.alpha.query.DriverQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class DriverManagerTest extends ITestBase {

    @Resource
    private DriverManager driverManager;

    @Test
    public void testInsert() {
        DriverDO driverDO = new DriverDO();
        driverDO.setName("测试姓名");
        driverDO.setSex("测试性别");
        driverDO.setCitizenId("3402231xx");
//        driverDO.setBirth("1990-05-10");
        driverDO.setEthnicGroup("测试民族");
        driverDO.setNativePlace("测试国籍");
        driverDO.setEducation("测试学历");
        driverDO.setMobilePhone("1320xxx");
        driverDO.setMailbox("测试邮箱");
        driverDO.setAddress("测试地址");
        driverDO.setDrivingLicense("测试驾照编号");
        driverDO.setLicenseClass("测试驾照类别");
//        driverDO.setLicenseStart("1990-05-11");
//        driverDO.setLicenseEnd("1990-05-12");
        driverDO.setOfferLicense("测试发证机关");
        driverDO.setRemark("测试备注");
        driverDO.setPersonUrl("测试地址1");
        driverDO.setLicenseUrl("测试地址2");
        driverDO.setStatus(SystemConstant.DRIVER_CAN_USE);
//        boolean res = driverManager.insert(driverDO);
//        System.out.println(res);
    }

    @Test
    public void testQuery() {
        DriverQuery driverQuery = new DriverQuery();
        driverQuery.setPage(1);
        driverQuery.setPageSize(10);
        driverQuery.setName("测试");
        List<DriverDO> list = driverManager.query(driverQuery);
        System.out.println(JSON.toJSON(list));
    }

    @Test
    public void testUpdate() {
        DriverDO driverDO = new DriverDO();
        driverDO.setId(1);
        driverDO.setName("测试姓名1");
        driverDO.setSex("测试性别1");
        driverDO.setCitizenId("3402231xx1");
//        driverDO.setBirth("1990-05-101");
        driverDO.setEthnicGroup("测试民族1");
        driverDO.setNativePlace("测试国籍1");
        driverDO.setEducation("测试学历1");
        driverDO.setMobilePhone("1320xxx1");
        driverDO.setMailbox("测试邮箱1");
        driverDO.setAddress("测试地址1");
        driverDO.setDrivingLicense("测试驾照编号1");
        driverDO.setLicenseClass("测试驾照类别1");
//        driverDO.setLicenseStart("1990-05-111");
//        driverDO.setLicenseEnd("1990-05-121");
        driverDO.setOfferLicense("测试发证机关1");
        driverDO.setRemark("测试备注1");
        driverDO.setPersonUrl("测试地址11");
        driverDO.setLicenseUrl("测试地址21");
        boolean res = driverManager.update(driverDO);
        System.out.println(res);
    }

    @Test
    public void testInvalid() {
        DriverDO driverDO = new DriverDO();
        driverDO.setId(1);
        driverDO.setStatus(SystemConstant.DRIVER_OFF_LINE);
        boolean res = driverManager.update(driverDO);
        System.out.println(res);
    }
}
