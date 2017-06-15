package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.ParamUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverDO;
import com.alpha.domain.SystemAccountDO;
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
                        @Param(name="gasCardNO", defaultValue = "") String gasCardNO, @Param("gasCardType") String gasCardType,
                        @Param(name="suTongCardNO", defaultValue="") String suTongCardNO, @Param("licenseClass") String licenseClass,
                        @Param(name="picUrl", defaultValue="") String picUrl, @Param("mile") int mile, Context context) {
        Result<String> result = new Result<String>();
        try {
            vehicleClass = (vehicleClass == null ? "" : vehicleClass);
            gasCardNO = (gasCardNO == null ? "" : gasCardNO);
            if (StringUtil.isBlank(gasCardNO)) {
                gasCardType = "";
            }
            gasCardType = (gasCardType == null ? "" : gasCardType);
            suTongCardNO = (suTongCardNO == null ? "" : suTongCardNO);
            picUrl = (picUrl == null ? "" : picUrl);
            SystemAccountDO systemAccountDO = this.getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!systemAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            vehicleNO = vehicleNO == null ? null : vehicleNO.trim();
            if (StringUtil.isBlank(vehicleNO) || !ParamUtil.validVehicleNO(vehicleNO)) {
                result.setErrMsg("请填写正确的车牌号");
                print(result);
                return;
            }
            engineNO = engineNO == null ? null : engineNO.trim();
            vin = vin == null ? null : vin.trim();
            VehicleDO vehicleDO = new VehicleDO();
            vehicleDO.setVehicleNO(vehicleNO);
            vehicleDO.setVehicleBrand(vehicleBrand);
            vehicleDO.setVehicleClass(vehicleClass);
            vehicleDO.setVehicleType(vehicleType);
            vehicleDO.setColour(colour);
            vehicleDO.setLoad(load);
            vehicleDO.setSeat(seat);
            vehicleDO.setTeam(team);
            vehicleDO.setCheckDate(checkDate == null ? null : new Date(checkDate));
            vehicleDO.setInsuranceDate(insuranceDate == null ? null : new Date(insuranceDate));
            vehicleDO.setMaintainDate(maintainDate == null ? null : new Date(maintainDate));
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
            String checkParamRes = checkParam(vehicleDO);
            if (!"ok".equals(checkParamRes)) {
                result.setErrMsg(checkParamRes);
                print(result);
                return;
            }
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

    private String checkParam(VehicleDO vehicleDO) {
        if (StringUtil.isBlank(vehicleDO.getVehicleNO())) {
            return "请填写车牌号";
        }
        if (StringUtil.isBlank(vehicleDO.getVehicleBrand())) {
            return "请设置车辆品牌";
        }
        if (StringUtil.isBlank(vehicleDO.getVehicleType())) {
            return "请填写车辆类型";
        }
        if (StringUtil.isBlank(vehicleDO.getColour())) {
            return "请填写车辆颜色";
        }
        if (StringUtil.isBlank(vehicleDO.getLoad())) {
            return "请填写载重";
        }
        if (vehicleDO.getSeat() <= 0) {
            return "请填写座位";
        }
        if (StringUtil.isBlank(vehicleDO.getTeam())) {
            return "请填写车队";
        }
        if (vehicleDO.getCheckDate() == null ) {
            return "请填写下一次年检时间";
        }
        if (vehicleDO.getInsuranceDate() == null) {
            return "请填写保险到期时间";
        }
        if (vehicleDO.getMaintainDate() == null ) {
            return "请填写下一次保养时间";
        }
        if (vehicleDO.getMaintainMile() <= 0) {
            return "请填写下一次保养里程";
        }
        if (StringUtil.isBlank(vehicleDO.getEngineNO())) {
            return "请填写发动机编号";
        }
        if (StringUtil.isBlank(vehicleDO.getVin())) {
            return "请填写车架号";
        }
        if (StringUtil.isBlank(vehicleDO.getLicenseClass())) {
            return "请填写驾照类型";
        }
        if (vehicleDO.getMile() <= 0) {
            return "请填写车辆里程";
        }
        return "ok";
    }
}
