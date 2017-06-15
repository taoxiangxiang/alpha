package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DepartmentDO;
import com.alpha.domain.InsuranceDO;
import com.alpha.query.DepartmentQuery;
import com.alpha.query.InsuranceQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class InsuranceManagerTest extends ITestBase {

    @Resource
    private InsuranceManager insuranceManager;

    @Test
    public void testInsert() {
        InsuranceDO insuranceDO = new InsuranceDO();
        insuranceDO.setVehicleNO("测试车牌");
        insuranceDO.setTeam("测试测队");
//        insuranceDO.setInsuranceStartDate(CalendarUtil.formatDate(new Date(), CalendarUtil.TIME_PATTERN));
//        insuranceDO.setInsuranceEndDate(CalendarUtil.formatDate(new Date(), CalendarUtil.TIME_PATTERN));
        insuranceDO.setType("1");
        insuranceDO.setMoney(2.0);
        insuranceDO.setCompanyName("3");
        insuranceDO.setOperator("4");
        insuranceDO.setFile("5");
        insuranceDO.setRemark("6");
//        boolean res = insuranceManager.insert(insuranceDO);
//        System.out.println("res=" + res);
    }

    @Test
    public void testQuery() {
        InsuranceQuery insuranceQuery = new InsuranceQuery();
        insuranceQuery.setPage(1);
        insuranceQuery.setPageSize(10);
        List<InsuranceDO> list = insuranceManager.query(insuranceQuery);
        System.out.println("res=" + JSON.toJSONString(list));
    }

    @Test
    public void testUpdate() {
        InsuranceDO insuranceDO = new InsuranceDO();
        insuranceDO.setId(1);
        insuranceDO.setVehicleNO("测试车牌2");
//        boolean res = insuranceManager.update(insuranceDO);
//        System.out.println("res=" + res);
    }
}
