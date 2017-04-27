package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DepartmentDO;
import com.alpha.domain.VehicleGasDO;
import com.alpha.query.DepartmentQuery;
import com.alpha.query.VehicleGasQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class VehicleGasManagerTest extends ITestBase {

    @Resource
    private VehicleGasManager vehicleGasManager;

    @Test
    public void testInsert() {
        VehicleGasDO vehicleGasDO = new VehicleGasDO();
        vehicleGasDO.setVehicleNO("1");
        vehicleGasDO.setTeam("2");
        vehicleGasDO.setGasDate(CalendarUtil.formatDate(new Date(), CalendarUtil.TIME_PATTERN));
        vehicleGasDO.setGasCardNO("3");
        vehicleGasDO.setGasAddress("4");
        vehicleGasDO.setPrice(5.0);
        vehicleGasDO.setCurMile(6);
        vehicleGasDO.setBeforeMile(7);
        vehicleGasDO.setPayType("8");
        vehicleGasDO.setGasType("9");
        vehicleGasDO.setMoney(10.0);
        vehicleGasDO.setAmount(11.0);
        vehicleGasDO.setOperator("12");
        vehicleGasDO.setFile("13");
        vehicleGasDO.setRemark("14");
        boolean res = vehicleGasManager.insert(vehicleGasDO);
        System.out.println("res=" + res);
    }

    @Test
    public void testQuery() {
        VehicleGasQuery vehicleGasQuery = new VehicleGasQuery();
        vehicleGasQuery.setPage(1);
        vehicleGasQuery.setPageSize(10);
        List<VehicleGasDO> list = vehicleGasManager.query(vehicleGasQuery);
        System.out.println("res=" + JSON.toJSONString(list));
    }

    @Test
    public void testUpdate() {
        VehicleGasDO vehicleGasDO = new VehicleGasDO();
        vehicleGasDO.setId(1);
        vehicleGasDO.setVehicleNO("sv");
        boolean res = vehicleGasManager.update(vehicleGasDO);
        System.out.println("res=" + res);
    }
}
