package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSON;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.*;
import com.alpha.manager.*;
import com.alpha.query.DriverBindQuery;
import com.alpha.query.DriverQuery;
import com.alpha.query.VehicleApplicationQuery;
import com.alpha.query.VehicleQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import com.alpha.web.module.screen.api.user.DriverBind;
import org.apache.ecs.html.B;

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
    private DriverBindManager driverBindManager;
    @Resource
    private VehicleApplicationManager vehicleApplicationManager;

    public void execute(@Param("applicationId") int applicationId,
                        @Param("useJson") String useJson, Context context) {
        Result<String> result = new Result<String>();
        try {
            SystemAccountDO systemAccountDO = getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请先登录系统"));
                return;
            }
            if (systemAccountDO.getAuthType() == null || !systemAccountDO.getAuthType().contains(SystemConstant.AUTH_TYPE_VEHICLE_USE_SCHEDULE)) {
                print(new Result<String>("您没有车辆调派的权限"));
                return;
            }
            if (applicationId <= 0) {
                result.setErrMsg("请核对申请单Id，申请单不存在");
                print(result);
                return;
            }
            VehicleApplicationDO vehicleApplicationDO = getVehicleApplication(applicationId);
            if (vehicleApplicationDO == null) {
                result.setErrMsg("请核对申请单Id，申请单不存在");
                print(result);
                return;
            }
            List<VehicleUseDO> vehicleUseDOList;
            try {
                vehicleUseDOList = JSON.parseArray(useJson, VehicleUseDO.class);
                if (vehicleUseDOList == null || vehicleUseDOList.size() == 0) {
                    result.setErrMsg("车辆调派信息错误，请核对车辆调派信息");
                    print(result);
                    return;
                }
            } catch (Exception e) {
                logger.error("VehicleUseAdd execute catch exception,useJson=" + useJson, e);
                result.setErrMsg("车辆调派信息错误，请核对车辆调派信息");
                print(result);
                return;
            }
            List<Integer> vehicleIdList = new ArrayList<Integer>();
            List<Integer> driverIdList = new ArrayList<Integer>();
            List<DriverBindDO> driverBindDOList = driverBindManager.query(new DriverBindQuery());
            Map<Integer, String> driverBindMap = new HashMap<Integer, String>();
            Map<String, String> driverBindReverseMap = new HashMap<String, String>();
            if (driverBindDOList != null) {
                for (DriverBindDO driverBindDO : driverBindDOList) {
                    driverBindMap.put(driverBindDO.getDriverId(), driverBindDO.getVehicleNO());
                    driverBindReverseMap.put(driverBindDO.getVehicleNO(), driverBindDO.getDriverName());
                }
            }
            Map<Integer, Boolean> driverIdSelectedMap = new HashMap<Integer, Boolean>();
            Map<Integer, Boolean> vehicleIdSelectedMap = new HashMap<Integer, Boolean>();
            for (VehicleUseDO vehicleUseDO : vehicleUseDOList) {
                if (vehicleUseDO.getVehicleId() == null || vehicleUseDO.getDriverId() == null ) {
                    result.setErrMsg("请核对车辆调派信息,请选择对应司机和车辆");
                    print(result);
                    return;
                }
                if (driverIdSelectedMap.containsKey(vehicleUseDO.getDriverId())) {
                    result.setErrMsg("请核对车辆调派信息,请勿重复调派司机：" + vehicleUseDO.getDriverName());
                    print(result);
                    return;
                }
                if (vehicleIdSelectedMap.containsKey(vehicleUseDO.getVehicleId())) {
                    result.setErrMsg("请核对车辆调派信息,请勿重复调派车：" + vehicleUseDO.getVehicleNO());
                    print(result);
                    return;
                }
                if (driverBindMap.containsKey(vehicleUseDO.getDriverId())
                        && !driverBindMap.get(vehicleUseDO.getDriverId()).equals(vehicleUseDO.getVehicleNO())) {
                    result.setErrMsg("请核对车辆调派信息,司机：" + vehicleUseDO.getDriverName() + "已经绑定了车辆" + driverBindMap.get(vehicleUseDO.getDriverId()));
                    print(result);
                    return;
                }
                if (driverBindReverseMap.containsKey(vehicleUseDO.getVehicleNO())
                        && !driverBindReverseMap.get(vehicleUseDO.getVehicleNO()).equals(vehicleUseDO.getDriverName())) {
                    result.setErrMsg("请核对车辆调派信息,车辆：" + vehicleUseDO.getVehicleNO() + "已经绑定了司机" + driverBindReverseMap.get(vehicleUseDO.getVehicleNO()));
                    print(result);
                    return;
                }
                driverIdSelectedMap.put(vehicleUseDO.getDriverId(), true);
                vehicleIdSelectedMap.put(vehicleUseDO.getVehicleId(), true);
                vehicleIdList.add(vehicleUseDO.getVehicleId());
                driverIdList.add(vehicleUseDO.getDriverId());
            }
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
                vehicleUseDO.setApplicantPhone(vehicleApplicationDO.getApplicantPhone());
                vehicleUseDO.setDriverName(driverDOMap.get(vehicleUseDO.getDriverId()).getName());
                vehicleUseDO.setDriverPhone(driverDOMap.get(vehicleUseDO.getDriverId()).getMobilePhone());
                vehicleUseDO.setTeam(driverDOMap.get(vehicleUseDO.getDriverId()).getTeam());
                vehicleUseDO.setActualStartDate(vehicleApplicationDO.getUseDate());
                vehicleUseDO.setStatus(SystemConstant.VEHICLE_USE_RECORD_EFFECTIVE);
            }
            String res = vehicleUseManager.batchInsert(vehicleApplicationDO, vehicleUseDOList);
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
