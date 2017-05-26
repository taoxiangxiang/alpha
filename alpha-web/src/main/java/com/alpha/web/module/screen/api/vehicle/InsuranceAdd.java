package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.InsuranceDO;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.InsuranceManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/27.
 */
public class InsuranceAdd extends BaseAjaxModule {

    @Resource
    private InsuranceManager insuranceManager;

    public void execute(@Param("vehicleNO") String vehicleNO, @Param("team") String team,
                        @Param("insuranceStartDate") Long insuranceStartDate, @Param("insuranceEndDate") Long insuranceEndDate,
                        @Param("type") String type, @Param("money") Double money,
                        @Param("companyName") String companyName, @Param("operator") String operator,
                        @Param("file") String file, @Param("remark") String remark, Context context) {
        Result<String> result = new Result<String>();
        try {
            InsuranceDO insuranceDO = new InsuranceDO();
            insuranceDO.setVehicleNO(vehicleNO);
            insuranceDO.setTeam(team);
            insuranceDO.setInsuranceStartDate(insuranceStartDate == null ? null : CalendarUtil.formatDate(new Date(insuranceStartDate), CalendarUtil.TIME_PATTERN));
            insuranceDO.setInsuranceEndDate(insuranceEndDate == null ? null : CalendarUtil.formatDate(new Date(insuranceEndDate), CalendarUtil.TIME_PATTERN));
            insuranceDO.setType(type);
            insuranceDO.setMoney(money);
            insuranceDO.setCompanyName(companyName);
            insuranceDO.setOperator(operator);
            insuranceDO.setFile(file);
            insuranceDO.setRemark(remark);
            boolean res = insuranceManager.insert(insuranceDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("InsuranceAdd execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
        }
        print(result);
    }
}
