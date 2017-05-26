package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.ParamUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.VehicleManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class VehicleAdd extends BaseAjaxModule {

    @Resource
    private VehicleManager vehicleManager;

    public void execute(@Param("vehicleNO") String vehicleNO, @Param("vehicleBrand") String vehicleBrand,
                        @Param("vehicleClass") String vehicleClass, @Param("vehicleType") String vehicleType,
                        @Param("colour") String colour, @Param("load") String load,
                        @Param("seat") int seat, @Param("team") String team,
                        @Param("checkDate") Long checkDate, @Param("insuranceDate") Long insuranceDate,
                        @Param("maintainDate") Long maintainDate, @Param("maintainMile") int maintainMile,
                        @Param("engineNO") String engineNO, @Param("vin") String vin,
                        @Param("gasCardNO") String gasCardNO, @Param("gasCardType") String gasCardType,
                        @Param("suTongCardNO") String suTongCardNO, @Param("licenseClass") String licenseClass,
                        @Param("picUrl") String picUrl, @Param("mile") int mile, Context context) {
        Result<String> result = new Result<String>();
        try {
            vehicleNO = vehicleNO == null ? null : vehicleNO.trim();
            if (StringUtil.isBlank(vehicleNO) || !ParamUtil.validVehicleNO(vehicleNO)) {
                result.setErrMsg("请填写正确的车牌号");
                print(result);
                return;
            }
            engineNO = engineNO == null ? null : engineNO.trim();
            if (StringUtil.isBlank(engineNO)) {
                result.setErrMsg("请填写正确的车牌号");
                print(result);
                return;
            }
            vin = vin == null ? null : vin.trim();
            if (StringUtil.isBlank(vin)) {
                result.setErrMsg("请填写正确的车架号");
                print(result);
                return;
            }
            VehicleDO vehicleDO = new VehicleDO();
            vehicleDO.setVehicleNO(vehicleNO);
            vehicleDO.setVehicleBrand(vehicleBrand);
            vehicleDO.setVehicleClass(vehicleClass);
            vehicleDO.setVehicleType(vehicleType);
            vehicleDO.setColour(colour);
            vehicleDO.setLoad(load);
            vehicleDO.setSeat(seat);
            vehicleDO.setTeam(team);
            vehicleDO.setCheckDate(checkDate == null ? null : CalendarUtil.formatDate(new Date(checkDate), CalendarUtil.TIME_PATTERN));
            vehicleDO.setInsuranceDate(insuranceDate == null ? null : CalendarUtil.formatDate(new Date(insuranceDate), CalendarUtil.TIME_PATTERN));
            vehicleDO.setMaintainDate(maintainDate == null ? null : CalendarUtil.formatDate(new Date(maintainDate), CalendarUtil.TIME_PATTERN));
            vehicleDO.setMaintainMile(maintainMile);
            vehicleDO.setEngineNO(engineNO);
            vehicleDO.setVin(vin);
            vehicleDO.setGasCardNO(gasCardNO);
            vehicleDO.setGasCardType(gasCardType);
            vehicleDO.setSuTongCardNO(suTongCardNO);
            vehicleDO.setLicenseClass(licenseClass);
            vehicleDO.setPicUrl(picUrl);
            vehicleDO.setMile(mile);
            vehicleDO.setStatus(SystemConstant.VEHICLE_CAN_USE);
            boolean res = vehicleManager.insert(vehicleDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("AddVehicle execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
        }
        print(result);
    }
}
