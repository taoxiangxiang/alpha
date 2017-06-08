package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleDO;
import com.alpha.domain.VehicleUseDO;
import com.alpha.manager.DriverManager;
import com.alpha.manager.VehicleManager;
import com.alpha.manager.VehicleUseManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import com.alpha.web.module.screen.api.vehicle.Vehicle;
import org.apache.commons.fileupload.FileItem;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/16.
 */
public class VehicleUseUpdate extends BaseAjaxModule{

    @Resource
    private VehicleUseManager vehicleUseManager;
    @Resource
    private DriverManager driverManager;
    @Resource
    private VehicleManager vehicleManager;

    public void execute(@Param("id") int id, @Param("actualStartDate") Long actualStartDate,
                        @Param("actualEndDate") Long actualEndDate, @Param("startMile") Integer startMile,
                        @Param("actualBackDate") Long actualBackDate, @Param("endMile") Integer endMile,
                        @Param("fuwuFee") Double fuwuFee, @Param("cailvFee") double cailvFee,
                        @Param("jiabanFee") double jiabanFee, @Param("guoluFee") double guoluFee,
                        @Param("guoqiaoFee") double guoqiaoFee, @Param("xicheFee") double xicheFee,
                        @Param("tingcheFee") double tingcheFee, @Param("otherFee") double otherFee,
                        @Param(name="remark", defaultValue="") String remark, @Param("attribute") String attribute,
                        Context context) {
        Result<String> result = new Result<String>();
        try {
            remark = (remark == null ? "" : remark);
            SystemAccountDO systemAccountDO = getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请先登录系统"));
                return;
            }
            VehicleUseDO vehicleUseDOInDB = vehicleUseManager.queryById(id);
            if (vehicleUseDOInDB == null) {
                result.setErrMsg("系统没有查询到对应的出车单");
                print(result);
                return;
            }
            if (vehicleUseDOInDB.getDriverId() != systemAccountDO.getDriverId() && !systemAccountDO.hasAuth()) {
                result.setErrMsg("这不是您的回车登记单");
                print(result);
                return;
            }
            if (actualStartDate == null || actualBackDate == null || actualEndDate == null) {
                result.setErrMsg("请填入车辆使用相关时间");
                print(result);
                return;
            }
            if (startMile == null || endMile == null) {
                result.setErrMsg("请填入车辆使用相关里程");
                print(result);
                return;
            }
            if (fuwuFee == null) {
                result.setErrMsg("请填入服务费");
                print(result);
                return;
            }

            DriverDO driverDO = driverManager.queryById(vehicleUseDOInDB.getDriverId());
            VehicleDO vehicleDO = vehicleManager.queryById(vehicleUseDOInDB.getVehicleId());
            if (driverDO == null || vehicleDO == null) {
                result.setErrMsg("系统没有查询到出车单对应的司机或车辆");
                print(result);
                return;
            }
            int applicationId = vehicleUseDOInDB.getApplicationId();
            if (SystemConstant.DRIVER_USING.equals(driverDO.getStatus()) && applicationId == driverDO.getApplicationId()) {
                driverDO.setStatus(SystemConstant.DRIVER_CAN_USE);
                driverDO.setApplicationId(0);
                if (!driverManager.update(driverDO)) {
                    result.setErrMsg("更新司机状态失败，请重新操作");
                    print(result);
                    return;
                }
            }
            if (SystemConstant.VEHICLE_USING.equals(vehicleDO.getStatus()) && applicationId == vehicleDO.getApplicationId()) {
                vehicleDO.setStatus(SystemConstant.VEHICLE_CAN_USE);
                vehicleDO.setApplicationId(0);
                if (!vehicleManager.update(vehicleDO)) {
                    result.setErrMsg("更新车辆状态失败，请重新操作");
                    print(result);
                    return;
                }
            }
            VehicleUseDO vehicleUseDO = new VehicleUseDO();
            vehicleUseDO.setId(id);
            vehicleUseDO.setActualStartDate(new Date(actualStartDate));
            vehicleUseDO.setActualBackDate(new Date(actualBackDate));
            vehicleUseDO.setActualEndDate(new Date(actualEndDate));
            vehicleUseDO.setUseTime((int)(actualEndDate/1000 - actualStartDate/1000));
            vehicleUseDO.setStartMile(startMile);
            vehicleUseDO.setEndMile(endMile);
            vehicleUseDO.setFuwuFee(fuwuFee);
            vehicleUseDO.setCailvFee(cailvFee);
            vehicleUseDO.setJiabanFee(jiabanFee);
            vehicleUseDO.setGuoluFee(guoluFee);
            vehicleUseDO.setGuoqiaoFee(guoqiaoFee);
            vehicleUseDO.setXicheFee(xicheFee);
            vehicleUseDO.setTingcheFee(tingcheFee);
            vehicleUseDO.setOtherFee(otherFee);
            vehicleUseDO.setRemark(remark);
            vehicleUseDO.setAttribute(attribute);
            boolean res = vehicleUseManager.update(vehicleUseDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("VehicleUseUpdate execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
        }
        print(result);
    }
}
