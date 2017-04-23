package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSON;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VehicleDO;
import com.alpha.domain.VehicleUseDO;
import com.alpha.manager.DriverManager;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.manager.VehicleManager;
import com.alpha.manager.VehicleUseManager;
import com.alpha.query.DriverQuery;
import com.alpha.query.VehicleApplicationQuery;
import com.alpha.query.VehicleQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import java.util.*;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/16.
 */
public class VehicleUseAdd extends BaseAjaxModule{

    @Resource
    private VehicleUseManager vehicleUseManager;
    @Resource
    private VehicleManager vehicleManager;
    @Resource
    private DriverManager driverManager;
    @Resource
    private VehicleApplicationManager vehicleApplicationManager;

    public void execute(@Param("applicationId") int applicationId,
                        @Param("useJson") String useJson, Context context) {
        Result<String> result = new Result<String>();
        try {
            VehicleApplicationDO vehicleApplicationDO = getVehicleApplication(applicationId);
            if (vehicleApplicationDO == null) {
                result.setErrMsg("请核对申请单Id，申请单不存在");
                print(result);
                return;
            }
            List<VehicleUseDO> vehicleUseDOList = JSON.parseArray(useJson, VehicleUseDO.class);
            List<Integer> vehicleIdList = new ArrayList<Integer>();
            List<Integer> driverIdList = new ArrayList<Integer>();
            List<VehicleDO> vehicleDOList = getUseVehicle(vehicleIdList);
            List<DriverDO> driverDOList = getUseDriver(driverIdList);
            if (vehicleDOList == null || vehicleIdList.size() != vehicleDOList.size()) {
                result.setErrMsg("请核对车辆Id信息，部分车辆Id不存在");
                print(result);
                return;
            }
            if (driverDOList == null || driverIdList.size() != driverDOList.size()) {
                result.setErrMsg("请核对司机Id信息，部分司机Id不存在");
                print(result);
                return;
            }
            Map<Integer, VehicleDO> vehicleDOMap = new HashMap<Integer, VehicleDO>();
            Map<Integer, DriverDO> driverDOMap = new HashMap<Integer, DriverDO>();
            for (VehicleDO vehicleDO : vehicleDOList) {
                if (!SystemConstant.VEHICLE_CAN_USE.equals(vehicleDO.getStatus())) {
                    result.setErrMsg("车牌" + vehicleDO.getVehicleNO() + "已经"
                            + (SystemConstant.VEHICLE_USING.equals(vehicleDO.getStatus()) ? "被他人使用中" : "下线了"));
                    print(result);
                    return;
                }
                vehicleDOMap.put(vehicleDO.getId(), vehicleDO);
            }

            for (DriverDO driverDO : driverDOList) {
                if (!SystemConstant.DRIVER_CAN_USE.equals(driverDO.getStatus())) {
                    result.setErrMsg("司机：" + driverDO.getName() + "已经"
                            + (SystemConstant.DRIVER_USING.equals(driverDO.getStatus()) ? "被他人使用中" : "下岗了"));
                    print(result);
                    return;
                }
                driverDOMap.put(driverDO.getId(), driverDO);
            }

            for (VehicleUseDO vehicleUseDO : vehicleUseDOList) {
                vehicleUseDO.setApplicationId(applicationId);
                vehicleUseDO.setVehicleNO(vehicleDOMap.get(vehicleUseDO.getVehicleId()).getVehicleNO());
                vehicleUseDO.setDriverName(driverDOMap.get(vehicleUseDO.getDriverId()).getName());
                vehicleUseDO.setActualStartDate(vehicleApplicationDO.getUseDate());
                vehicleUseDO.setStatus(SystemConstant.VEHICLE_USE_RECORD_EFFECTIVE);
            }
            String res = vehicleUseManager.batchInsert(vehicleUseDOList);
            if (res.equals("true")) {
                result.setData("操作成功");
            } else {
                result.setErrMsg(res);
            }
        } catch (Exception e) {
            logger.error("VehicleUseAdd execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
        }
        print(result);
    }

    private VehicleApplicationDO getVehicleApplication(int applicationId) {
        VehicleApplicationQuery vehicleApplicationQuery = new VehicleApplicationQuery();
        vehicleApplicationQuery.setId(applicationId);
        List<VehicleApplicationDO> list = vehicleApplicationManager.query(vehicleApplicationQuery);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    private List<VehicleDO> getUseVehicle(List<Integer> vehicleIdList) {
        VehicleQuery vehicleQuery = new VehicleQuery();
        vehicleQuery.setIdList(vehicleIdList);
        return vehicleManager.query(vehicleQuery);
    }

    private List<DriverDO> getUseDriver(List<Integer> driverIdList) {
        DriverQuery driverQuery = new DriverQuery();
        driverQuery.setIdList(driverIdList);
        return driverManager.query(driverQuery);
    }
}
