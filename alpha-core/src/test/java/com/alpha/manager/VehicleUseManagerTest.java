package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alpha.ITestBase;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DepartmentDO;
import com.alpha.domain.VehicleUseDO;
import com.alpha.query.DepartmentQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class VehicleUseManagerTest extends ITestBase {

    @Resource
    private VehicleUseManager vehicleUseManager;

    @Test
    public void testInsert() throws Exception{
        String useJson = "[{\"driverId\":1,\"vehicleId\":2}]";
        List<VehicleUseDO> vehicleUseDOList = JSON.parseArray(useJson, VehicleUseDO.class);
        System.out.println(vehicleUseManager.batchInsert(vehicleUseDOList));
    }

    @Test
    public void testQuery() {
        VehicleUseDO vehicleUseDO = new VehicleUseDO();
        vehicleUseDO.setDriverId(1);
        vehicleUseDO.setVehicleId(2);
        List<VehicleUseDO> list = new ArrayList<VehicleUseDO>();
        System.out.println("res=" + JSON.toJSONString(list));
    }

    @Test
    public void testUpdate() {
        VehicleUseDO vehicleUseDO = new VehicleUseDO();
        vehicleUseDO.setId(1);
        vehicleUseDO.setStartMile(12);
        vehicleUseDO.setEndMile(13);
        vehicleUseDO.setFuwuFee(1.1);
        vehicleUseDO.setCailvFee(2.2);
        vehicleUseDO.setJiabanFee(3.3);
        vehicleUseDO.setGuoluFee(4.4);
        vehicleUseDO.setGuoqiaoFee(5.5);
        vehicleUseDO.setXicheFee(6.6);
        vehicleUseDO.setTingcheFee(7.7);
        vehicleUseDO.setOtherFee(8.8);
        vehicleUseDO.setRemark("测试备注");
        JSONObject jsonObject = new JSONObject();
        if (vehicleUseDO.getAttribute() != null) {
            jsonObject = JSON.parseObject(vehicleUseDO.getAttribute());
        }
        jsonObject.put("file", "12");
        vehicleUseDO.setAttribute(jsonObject.toJSONString());
        boolean res = vehicleUseManager.update(vehicleUseDO);
        System.out.println("res=" + res);
    }
}
