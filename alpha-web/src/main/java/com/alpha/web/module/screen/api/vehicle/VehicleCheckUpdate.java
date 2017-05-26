package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.InsuranceDO;
import com.alpha.domain.VehicleCheckDO;
import com.alpha.manager.InsuranceManager;
import com.alpha.manager.VehicleCheckManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/27.
 */
public class VehicleCheckUpdate extends BaseAjaxModule {

    @Resource
    private VehicleCheckManager vehicleCheckManager;

    public void execute(@Param("vehicleNO") String vehicleNO, @Param("team") String team,
                        @Param("checkDate") Long checkDate, @Param("checkNO") String checkNO,
                        @Param("checkAddress") String checkAddress, @Param("money") Double money,
                        @Param("endDate") Long endDate, @Param("operator") String operator,
                        @Param("file") String file, @Param("remark") String remark,
                        @Param("id") Integer id, Context context) {
        Result<String> result = new Result<String>();
        try {
            VehicleCheckDO vehicleCheckDO = new VehicleCheckDO();
            vehicleCheckDO.setId(id);
            vehicleCheckDO.setVehicleNO(vehicleNO);
            vehicleCheckDO.setTeam(team);
            vehicleCheckDO.setCheckDate(checkDate == null ? null : CalendarUtil.formatDate(new Date(checkDate), CalendarUtil.TIME_PATTERN));
            vehicleCheckDO.setEndDate(endDate == null ? null : CalendarUtil.formatDate(new Date(endDate), CalendarUtil.TIME_PATTERN));
            vehicleCheckDO.setCheckAddress(checkAddress);
            vehicleCheckDO.setMoney(money);
            vehicleCheckDO.setCheckNO(checkNO);
            vehicleCheckDO.setOperator(operator);
            vehicleCheckDO.setFile(file);
            vehicleCheckDO.setRemark(remark);
            boolean res = vehicleCheckManager.update(vehicleCheckDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("VehicleCheckUpdate execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
        }
        print(result);
    }
}
