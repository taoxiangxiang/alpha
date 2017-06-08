package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleGasDO;
import com.alpha.manager.VehicleGasManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/27.
 */
public class VehicleGasUpdate extends BaseAjaxModule {

    @Resource
    private VehicleGasManager vehicleGasManager;

    public void execute(@Param("vehicleNO") String vehicleNO, @Param("team") String team,
                        @Param("gasDate") Long gasDate, @Param(name="gasCardNO", defaultValue="") String gasCardNO,
                        @Param(name="gasAddress", defaultValue="") String gasAddress, @Param("price") Double price,
                        @Param("money") Double money, @Param("amount") Double amount,
                        @Param("curMile") Integer curMile, @Param("beforeMile") Integer beforeMile,
                        @Param(name="payType", defaultValue="") String payType, @Param("gasType") String gasType,
                        @Param("operator") String operator,
                        @Param(name="file", defaultValue="") String file, @Param(name="remark", defaultValue="") String remark,
                        @Param("id") Integer id, Context context) {
        Result<String> result = new Result<String>();
        try {
            file = (file == null ? "" : file);
            remark = (remark == null ? "" : remark);
            gasCardNO = (gasCardNO == null ? "" : gasCardNO);
            gasAddress = (gasAddress == null ? "" : gasAddress);
            payType = (payType == null ? "" : payType);
            SystemAccountDO systemAccountDO = this.getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!systemAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            VehicleGasDO vehicleGasDO = new VehicleGasDO();
            vehicleGasDO.setId(id);
            vehicleGasDO.setVehicleNO(vehicleNO);
            vehicleGasDO.setTeam(team);
            vehicleGasDO.setGasDate(gasDate == null ? null :new Date(gasDate));
            vehicleGasDO.setGasCardNO(gasCardNO);
            vehicleGasDO.setGasAddress(gasAddress);
            vehicleGasDO.setAmount(amount);
            vehicleGasDO.setPrice(price);
            vehicleGasDO.setCurMile(curMile);
            vehicleGasDO.setBeforeMile(beforeMile);
            vehicleGasDO.setPayType(payType);
            vehicleGasDO.setGasType(gasType);
            vehicleGasDO.setMoney(money);
            vehicleGasDO.setOperator(operator);
            vehicleGasDO.setFile(file);
            vehicleGasDO.setRemark(remark);
            String checkParamRes = checkParam(vehicleGasDO);
            if (!"ok".equals(checkParamRes)) {
                result.setErrMsg(checkParamRes);
                print(result);
                return;
            }
            boolean res = vehicleGasManager.update(vehicleGasDO);
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

    private String checkParam(VehicleGasDO vehicleGasDO) {
        if (StringUtil.isBlank(vehicleGasDO.getVehicleNO())) {
            return "请填写车牌号";
        }
        if (StringUtil.isBlank(vehicleGasDO.getTeam())) {
            return "请设置车牌对应的车队";
        }
        if (vehicleGasDO.getGasDate() == null) {
            return "请填写加油日期";
        }
        if (vehicleGasDO.getGasType() == null) {
            return "请填写油料标号";
        }
        if (vehicleGasDO.getPrice() == null) {
            return "请填写单价";
        }
        if (vehicleGasDO.getMoney() == null) {
            return "请填写金额";
        }
        if (vehicleGasDO.getAmount() == null) {
            return "请填写加油量";
        }
        if (vehicleGasDO.getCurMile() == null) {
            return "请填写本次加油里程";
        }
        if (vehicleGasDO.getBeforeMile() == null) {
            return "请填写上次加油里程";
        }
        if (StringUtil.isBlank(vehicleGasDO.getOperator())) {
            return "请填写经手人";
        }
        return "ok";
    }
}
