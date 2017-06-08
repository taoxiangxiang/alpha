package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.InsuranceDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleCheckDO;
import com.alpha.manager.VehicleCheckManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/27.
 */
public class VehicleCheckAdd extends BaseAjaxModule {

    @Resource
    private VehicleCheckManager vehicleCheckManager;

    public void execute(@Param("vehicleNO") String vehicleNO, @Param("team") String team,
                        @Param("checkDate") Long checkDate, @Param("checkNO") String checkNO,
                        @Param("checkAddress") String checkAddress, @Param("money") Double money,
                        @Param("endDate") Long endDate, @Param("operator") String operator,
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
            VehicleCheckDO vehicleCheckDO = new VehicleCheckDO();
            vehicleCheckDO.setVehicleNO(vehicleNO);
            vehicleCheckDO.setTeam(team);
            vehicleCheckDO.setCheckDate(checkDate == null ? null : new Date(checkDate));
            vehicleCheckDO.setEndDate(endDate == null ? null : new Date(endDate));
            vehicleCheckDO.setCheckAddress(checkAddress);
            vehicleCheckDO.setMoney(money);
            vehicleCheckDO.setCheckNO(checkNO);
            vehicleCheckDO.setOperator(operator);
            vehicleCheckDO.setFile(file);
            vehicleCheckDO.setRemark(remark);
            String checkParamRes = checkParam(vehicleCheckDO);
            if (!"ok".equals(checkParamRes)) {
                result.setErrMsg(checkParamRes);
                print(result);
                return;
            }
            boolean res = vehicleCheckManager.insert(vehicleCheckDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("VehicleCheckAdd execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
        }
        print(result);
    }

    private String checkParam(VehicleCheckDO vehicleCheckDO) {
        if (StringUtil.isBlank(vehicleCheckDO.getVehicleNO())) {
            return "请填写车牌号";
        }
        if (StringUtil.isBlank(vehicleCheckDO.getTeam())) {
            return "请设置车牌对应的车队";
        }
        if (vehicleCheckDO.getCheckDate() == null) {
            return "请填写年检日期";
        }
        if (vehicleCheckDO.getCheckNO() == null) {
            return "请填写年检号";
        }
        if (vehicleCheckDO.getMoney() == null) {
            return "请填写年检费用";
        }
        if (StringUtil.isBlank(vehicleCheckDO.getCheckAddress())) {
            return "请填写车管所";
        }
        if (vehicleCheckDO.getEndDate() == null) {
            return "请填写到期时间";
        }
        if (StringUtil.isBlank(vehicleCheckDO.getOperator())) {
            return "请填写经手人";
        }
        return "ok";
    }
}
