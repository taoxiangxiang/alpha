package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.VehicleGasDO;
import com.alpha.domain.VehicleIllegalDO;
import com.alpha.query.VehicleGasQuery;
import com.alpha.query.VehicleIllegalQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class VehicleIllegalManagerTest extends ITestBase {

    @Resource
    private VehicleIllegalManager vehicleIllegalManager;

    @Test
    public void testInsert() {
        VehicleIllegalDO vehicleIllegalDO = new VehicleIllegalDO();
        vehicleIllegalDO.setVehicleNO("1");
        vehicleIllegalDO.setTeam("2");
        vehicleIllegalDO.setIllegalDate(CalendarUtil.formatDate(new Date(), CalendarUtil.TIME_PATTERN));
        vehicleIllegalDO.setReason("3");
        vehicleIllegalDO.setIllegalAddress("4");
        vehicleIllegalDO.setDriverId(5);
        vehicleIllegalDO.setDriverName("6");
        vehicleIllegalDO.setMoney(7.0);
        vehicleIllegalDO.setPoint(8);
        vehicleIllegalDO.setFile("9");
        vehicleIllegalDO.setRemark("10");
        boolean res = vehicleIllegalManager.insert(vehicleIllegalDO);
        System.out.println("res=" + res);
    }

    @Test
    public void testQuery() {
        VehicleIllegalQuery vehicleIllegalQuery = new VehicleIllegalQuery();
        vehicleIllegalQuery.setPage(1);
        vehicleIllegalQuery.setPageSize(10);
        List<VehicleIllegalDO> list = vehicleIllegalManager.query(vehicleIllegalQuery);
        System.out.println("res=" + JSON.toJSONString(list));
    }

    @Test
    public void testUpdate() {
        VehicleIllegalDO vehicleIllegalDO = new VehicleIllegalDO();
        vehicleIllegalDO.setId(1);
        vehicleIllegalDO.setVehicleNO("sb");
        boolean res = vehicleIllegalManager.update(vehicleIllegalDO);
        System.out.println("res=" + res);
    }
}
