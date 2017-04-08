package com.alpha.web.module.screen.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.VehicleManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import com.taobao.util.CalendarUtil;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class VehicleUpdate extends BaseAjaxModule {

    @Resource
    private VehicleManager vehicleManager;

    public void execute(@Param("vehicleNO") String vehicleNO, @Param("vehicleBrand") String vehicleBrand,
                        @Param("vehicleClass") String vehicleClass, @Param("vehicleType") String vehicleType,
                        @Param("colour") String colour, @Param("load") String load,
                        @Param("seat") int seat, @Param("team") String team,
                        @Param("checkDate") Date checkDate, @Param("insuranceDate") Date insuranceDate,
                        @Param("maintainDate") Date maintainDate, @Param("maintainMile") String maintainMile,
                        @Param("engineNO") String engineNO, @Param("vin") String vin,
                        @Param("gasCardNO") String gasCardNO, @Param("gasCardType") String gasCardType,
                        @Param("suTongCardNO") String suTongCardNO, @Param("licenseClass") String licenseClass,
                        @Param("picUrl") String picUrl, @Param("mile") String mile,
                        @Param("id") Long id, Context context) {
        Result<String> result = new Result<String>();
        try {
            VehicleDO vehicleDO = new VehicleDO();
            vehicleDO.setId(id);
            vehicleDO.setVehicleNO(vehicleNO);
            vehicleDO.setVehicleBrand(vehicleBrand);
            vehicleDO.setVehicleClass(vehicleClass);
            vehicleDO.setVehicleType(vehicleType);
            vehicleDO.setColour(colour);
            vehicleDO.setLoad(load);
            vehicleDO.setSeat(seat);
            vehicleDO.setTeam(team);
            vehicleDO.setCheckDate(CalendarUtil.formatDate(checkDate, CalendarUtil.TIME_PATTERN));
            vehicleDO.setInsuranceDate(CalendarUtil.formatDate(insuranceDate, CalendarUtil.TIME_PATTERN));
            vehicleDO.setMaintainDate(CalendarUtil.formatDate(maintainDate, CalendarUtil.TIME_PATTERN));
            vehicleDO.setMaintainMile(maintainMile);
            vehicleDO.setEngineNO(engineNO);
            vehicleDO.setVin(vin);
            vehicleDO.setGasCardNO(gasCardNO);
            vehicleDO.setGasCardType(gasCardType);
            vehicleDO.setSuTongCardNO(suTongCardNO);
            vehicleDO.setLicenseClass(licenseClass);
            vehicleDO.setPicUrl(picUrl);
            vehicleDO.setMile(mile);
            boolean res = vehicleManager.update(vehicleDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("UpdateVehicle execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
        }
        print(result);
    }
}
