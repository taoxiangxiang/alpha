package com.alpha.manager;

import com.alibaba.fastjson.JSON;
import com.alpha.ITestBase;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleDO;
import com.alpha.query.VehicleApplicationQuery;
import com.alpha.query.VehicleQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class VehicleApplicationManagerTest extends ITestBase {


    @Resource
    private VehicleApplicationManager vehicleApplicationManager;

    @Test
    public void testInsert() {
        VehicleApplicationDO vehicleApplicationDO = new VehicleApplicationDO();
        vehicleApplicationDO.setApplicationType(SystemConstant.applicationTypeMap.get("emergency"));
        vehicleApplicationDO.setVehicleType("car");
        vehicleApplicationDO.setApplicationReason(SystemConstant.applicationReasonMap.get("1"));
//        vehicleApplicationDO.setUseDate("1990-01-01 00:00:00");
//        vehicleApplicationDO.setPredictBackDate("1990-01-01 00:00:01");
        vehicleApplicationDO.setApplicant("测试申请人");
        vehicleApplicationDO.setApplicationDepartment("测试部门");
        vehicleApplicationDO.setApplicantPhone("测试电话");
        vehicleApplicationDO.setPersonNumber(1);
        vehicleApplicationDO.setStartPlace("测试开始地址");
        vehicleApplicationDO.setEndPlace("测试结束地址");
        vehicleApplicationDO.setEndPlaceType(SystemConstant.endPlaceTypeMap.get("outOfCity"));
        vehicleApplicationDO.setContacts("测试联系人");
        vehicleApplicationDO.setContactsPhone("测试联系电话");
        vehicleApplicationDO.setRemark("测试备注");
        vehicleApplicationDO.setFile("测试文件地址");
        if (!SystemConstant.END_PLACE_IN_CITY.equals("outOfCity")) {
            vehicleApplicationDO.setStatus(SystemConstant.VEHICLE_IN_CITY_WAIT_VERIFY);
        } else {
            vehicleApplicationDO.setStatus(SystemConstant.VEHICLE_OUT_OF_CITY_WAIT_FIRST_VERIFY);
        }
        boolean res = vehicleApplicationManager.insert(vehicleApplicationDO);
        System.out.println("res=" + res);
    }

    @Test
    public void testQuery() {
        VehicleApplicationQuery vehicleApplicationQuery = new VehicleApplicationQuery();
        vehicleApplicationQuery.setStartDate(CalendarUtil.toDate("2017-04-15 00:00:00",CalendarUtil.TIME_PATTERN));
        vehicleApplicationQuery.setEndDate(CalendarUtil.toDate("2017-04-16 00:00:00",CalendarUtil.TIME_PATTERN));
        List<String> statusList = new ArrayList<String>();
        statusList.add("waitFirstVerify");
        vehicleApplicationQuery.setStatusList(statusList);
        List<VehicleApplicationDO> list = vehicleApplicationManager.query(vehicleApplicationQuery);
        System.out.println("list=" + JSON.toJSONString(list));
    }

}
