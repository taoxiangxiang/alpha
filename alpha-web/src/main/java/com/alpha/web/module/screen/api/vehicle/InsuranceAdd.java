package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.domain.InsuranceDO;
import com.alpha.domain.SystemAccountDO;
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
                        @Param(name="file", defaultValue="") String file, @Param(name="remark", defaultValue="") String remark, Context context) {
        Result<String> result = new Result<String>();
        try {
            file = (file == null ? "" : file);
            remark = (remark == null ? "" : remark);
            SystemAccountDO systemAccountDO = this.getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!systemAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            InsuranceDO insuranceDO = new InsuranceDO();
            insuranceDO.setVehicleNO(vehicleNO);
            insuranceDO.setTeam(team);
            insuranceDO.setInsuranceStartDate(insuranceStartDate == null ? null : new Date(insuranceStartDate));
            insuranceDO.setInsuranceEndDate(insuranceEndDate == null ? null : new Date(insuranceEndDate));
            insuranceDO.setType(type);
            insuranceDO.setMoney(money);
            insuranceDO.setCompanyName(companyName);
            insuranceDO.setOperator(operator);
            insuranceDO.setFile(file);
            insuranceDO.setRemark(remark);
            String checkParamRes = checkParam(insuranceDO);
            if (!"ok".equals(checkParamRes)) {
                result.setErrMsg(checkParamRes);
                print(result);
                return;
            }
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

    private String checkParam(InsuranceDO insuranceDO) {
        if (StringUtil.isBlank(insuranceDO.getVehicleNO())) {
            return "请填写车牌号";
        }
        if (StringUtil.isBlank(insuranceDO.getTeam())) {
            return "请设置车牌对应的车队";
        }
        if (insuranceDO.getInsuranceStartDate() == null) {
            return "请填写投保日期";
        }
        if (insuranceDO.getInsuranceEndDate() == null) {
            return "请填写保险到期";
        }
        if (StringUtil.isBlank(insuranceDO.getType())) {
            return "请填写保险种类";
        }
        if (insuranceDO.getMoney() == null) {
            return "请填写投保金额";
        }
        if (StringUtil.isBlank(insuranceDO.getCompanyName())) {
            return "请填写保险公司";
        }
        if (StringUtil.isBlank(insuranceDO.getOperator())) {
            return "请填写经手人";
        }
        return "ok";
    }
}
