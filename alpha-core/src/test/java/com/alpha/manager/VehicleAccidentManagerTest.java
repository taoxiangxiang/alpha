package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.VehicleAccidentDO;
import com.alpha.query.VehicleAccidentQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class VehicleAccidentManagerTest extends ITestBase {

    @Resource
    private VehicleAccidentManager vehicleAccidentManager;

    @Test
    public void testInsert() {
        VehicleAccidentDO vehicleAccidentDO = new VehicleAccidentDO();
        vehicleAccidentDO.setVehicleNO("1");
        vehicleAccidentDO.setTeam("2");
        vehicleAccidentDO.setAccidentDate(CalendarUtil.formatDate(new Date(), CalendarUtil.TIME_PATTERN));
        vehicleAccidentDO.setAccidentDesc("3");
        vehicleAccidentDO.setAccidentAddress("4");
        vehicleAccidentDO.setDriverId(5);
        vehicleAccidentDO.setDriverName("6");
        vehicleAccidentDO.setMoney(7.0);
        vehicleAccidentDO.setResult("8");
        vehicleAccidentDO.setDealDesc("9");
        vehicleAccidentDO.setLiableDesc("10");
        vehicleAccidentDO.setLiablePerson("11");
        vehicleAccidentDO.setMaintainAddress("12");
        vehicleAccidentDO.setFile("13");
        vehicleAccidentDO.setRemark("14");
        boolean res = vehicleAccidentManager.insert(vehicleAccidentDO);
        System.out.println("res=" + res);
    }

    @Test
    public void testQuery() {
        VehicleAccidentQuery vehicleAccidentQuery = new VehicleAccidentQuery();
        vehicleAccidentQuery.setPage(1);
        vehicleAccidentQuery.setPageSize(10);
        List<VehicleAccidentDO> list = vehicleAccidentManager.query(vehicleAccidentQuery);
        System.out.println("res=" + JSON.toJSONString(list));
    }

    @Test
    public void testUpdate() {
        VehicleAccidentDO vehicleAccidentDO = new VehicleAccidentDO();
        vehicleAccidentDO.setId(1);
        vehicleAccidentDO.setVehicleNO("sb");
        boolean res = vehicleAccidentManager.update(vehicleAccidentDO);
        System.out.println("res=" + res);
    }
}
