package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverBindDO;
import com.alpha.domain.DriverDO;
import com.alpha.query.DriverBindQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class DriverBindManagerTest extends ITestBase {

    @Resource
    private DriverBindManager driverBindManager;

    @Test
    public void testInsert() {
        DriverBindDO driverBindDO = new DriverBindDO();
        driverBindDO.setVehicleId(1);
        driverBindDO.setVehicleNO("测试车排");
        driverBindDO.setDriverId(1);
        driverBindDO.setDriverName("测试司机");
        driverBindDO.setTeam("测试测队");
        driverBindDO.setBindDate(new Date());
        driverBindDO.setStatus(SystemConstant.DRIVER_BIND_VEHICLE);
        boolean res = driverBindManager.insert(driverBindDO);
        System.out.println("res=" + res);
    }

    @Test
    public void testQuery() {
        DriverBindQuery driverBindQuery = new DriverBindQuery();
        List<DriverBindDO> list = driverBindManager.query(driverBindQuery);
        int number = driverBindManager.count(driverBindQuery);
        System.out.println("number=" + number);
        System.out.println("list=" + JSON.toJSONString(list));
    }

    @Test
    public void testUpdate() {
        DriverBindDO driverBindDO = new DriverBindDO();
        driverBindDO.setId(1);
        driverBindDO.setStatus(SystemConstant.DRIVER_UNBIND_VEHICLE);
        boolean res = driverBindManager.update(driverBindDO);
        System.out.println("res=" + res);
    }
}
