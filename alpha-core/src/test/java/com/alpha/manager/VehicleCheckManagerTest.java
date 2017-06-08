package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DepartmentDO;
import com.alpha.domain.VehicleCheckDO;
import com.alpha.query.DepartmentQuery;
import com.alpha.query.VehicleCheckQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
public class VehicleCheckManagerTest extends ITestBase {

    @Resource
    private VehicleCheckManager vehicleCheckManager;

    @Test
    public void testInsert() {
        VehicleCheckDO vehicleCheckDO = new VehicleCheckDO();
        vehicleCheckDO.setVehicleNO("1");
        vehicleCheckDO.setTeam("2");
//        vehicleCheckDO.setCheckDate(CalendarUtil.formatDate(new Date(), CalendarUtil.TIME_PATTERN));
//        vehicleCheckDO.setEndDate(CalendarUtil.formatDate(new Date(), CalendarUtil.TIME_PATTERN));
        vehicleCheckDO.setCheckAddress("3");
        vehicleCheckDO.setMoney(4.0);
        vehicleCheckDO.setCheckNO("5");
        vehicleCheckDO.setOperator("6");
        vehicleCheckDO.setFile("7");
        vehicleCheckDO.setRemark("8");
        boolean res = vehicleCheckManager.insert(vehicleCheckDO);
        System.out.println("res=" + res);
    }

    @Test
    public void testQuery() {
        VehicleCheckQuery vehicleCheckQuery = new VehicleCheckQuery();
        vehicleCheckQuery.setPage(1);
        vehicleCheckQuery.setPageSize(10);
        List<VehicleCheckDO> list = vehicleCheckManager.query(vehicleCheckQuery);
        System.out.println("res=" + JSON.toJSONString(list));
    }

    @Test
    public void testUpdate() {
        VehicleCheckDO vehicleCheckDO = new VehicleCheckDO();
        vehicleCheckDO.setId(1);
        vehicleCheckDO.setVehicleNO("12");
        boolean res = vehicleCheckManager.update(vehicleCheckDO);
        System.out.println("res=" + res);
    }
}
