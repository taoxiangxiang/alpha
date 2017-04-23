package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.VehicleDO;
import com.alpha.query.VehicleQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class VehicleManagerTest extends ITestBase {


    @Resource
    private VehicleManager vehicleManager;


    @Test
    public void testInsert() {
        VehicleDO vehicleDO = new VehicleDO();
        vehicleDO.setVehicleNO("测试编号");
        vehicleDO.setVehicleBrand("测试品牌");
        vehicleDO.setVehicleClass("测试豪华版");
        vehicleDO.setVehicleType("卡车");
        vehicleDO.setColour("蓝色");
        vehicleDO.setLoad("1顿");
        vehicleDO.setSeat(2);
        vehicleDO.setTeam("测试车队");
        vehicleDO.setCheckDate("2017-10-1 00:00:01");
        vehicleDO.setInsuranceDate("2017-10-1 00:00:02");
        vehicleDO.setMaintainDate("2017-10-1 00:00:03");
        vehicleDO.setMaintainMile(1000);
        vehicleDO.setEngineNO("测试引擎编号");
        vehicleDO.setVin("测试车架号");
        vehicleDO.setGasCardNO("测试油卡编号");
        vehicleDO.setGasCardType("测试油卡类型");
        vehicleDO.setSuTongCardNO("测试苏通卡号");
        vehicleDO.setLicenseClass("测试驾照");
        vehicleDO.setPicUrl("测试地址");
        vehicleDO.setMile(1111);
        vehicleDO.setStatus(SystemConstant.VEHICLE_CAN_USE);
        boolean res = vehicleManager.insert(vehicleDO);
        System.out.println("res=" + res);
    }

    @Test
    public void testQuery() {
        VehicleQuery vehicleQuery = new VehicleQuery();
        List<VehicleDO> list = vehicleManager.query(vehicleQuery);
        int number = vehicleManager.count(vehicleQuery);
        System.out.println("number=" + number);
        System.out.println("list=" + JSON.toJSONString(list));
    }

    @Test
    public void testUpdate() {
        VehicleDO vehicleDO = new VehicleDO();
        vehicleDO.setId(1);
        vehicleDO.setVehicleNO("测试编号更新");
        boolean res = vehicleManager.update(vehicleDO);
        System.out.println("res=" + res);
    }
}
