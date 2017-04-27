package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.InsuranceDO;
import com.alpha.manager.InsuranceManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/27.
 */
public class InsuranceUpdate extends BaseAjaxModule {

    @Resource
    private InsuranceManager insuranceManager;

    public void execute(@Param("vehicleNO") String vehicleNO, @Param("team") String team,
                        @Param("insuranceStartDate") Date insuranceStartDate, @Param("insuranceEndDate") Date insuranceEndDate,
                        @Param("type") String type, @Param("money") Double money,
                        @Param("companyName") String companyName, @Param("operator") String operator,
                        @Param("file") String file, @Param("remark") String remark,
                        @Param("id") Integer id, Context context) {
        Result<String> result = new Result<String>();
        try {
            InsuranceDO insuranceDO = new InsuranceDO();
            insuranceDO.setId(id);
            insuranceDO.setVehicleNO(vehicleNO);
            insuranceDO.setTeam(team);
            insuranceDO.setInsuranceStartDate(CalendarUtil.formatDate(insuranceStartDate, CalendarUtil.TIME_PATTERN));
            insuranceDO.setInsuranceEndDate(CalendarUtil.formatDate(insuranceEndDate, CalendarUtil.TIME_PATTERN));
            insuranceDO.setType(type);
            insuranceDO.setMoney(money);
            insuranceDO.setCompanyName(companyName);
            insuranceDO.setOperator(operator);
            insuranceDO.setFile(file);
            insuranceDO.setRemark(remark);
            boolean res = insuranceManager.update(insuranceDO);
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
