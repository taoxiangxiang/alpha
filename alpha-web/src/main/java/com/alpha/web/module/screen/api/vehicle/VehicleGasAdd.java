package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.VehicleGasDO;
import com.alpha.manager.VehicleGasManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/27.
 */
public class VehicleGasAdd extends BaseAjaxModule {

    @Resource
    private VehicleGasManager vehicleGasManager;

    public void execute(@Param("vehicleNO") String vehicleNO, @Param("team") String team,
                        @Param("gasDate") Date gasDate, @Param("gasCardNO") String gasCardNO,
                        @Param("gasAddress") String gasAddress, @Param("price") Double price,
                        @Param("money") Double money, @Param("amount") Double amount,
                        @Param("curMile") Integer curMile, @Param("beforeMile") Integer beforeMile,
                        @Param("payType") String payType, @Param("gasType") String gasType,
                        @Param("operator") String operator,
                        @Param("file") String file, @Param("remark") String remark, Context context) {
        Result<String> result = new Result<String>();
        try {
            VehicleGasDO vehicleGasDO = new VehicleGasDO();
            vehicleGasDO.setVehicleNO(vehicleNO);
            vehicleGasDO.setTeam(team);
            vehicleGasDO.setGasDate(CalendarUtil.formatDate(gasDate, CalendarUtil.TIME_PATTERN));
            vehicleGasDO.setGasCardNO(gasCardNO);
            vehicleGasDO.setAmount(amount);
            vehicleGasDO.setGasAddress(gasAddress);
            vehicleGasDO.setPrice(price);
            vehicleGasDO.setCurMile(curMile);
            vehicleGasDO.setBeforeMile(beforeMile);
            vehicleGasDO.setPayType(payType);
            vehicleGasDO.setGasType(gasType);
            vehicleGasDO.setMoney(money);
            vehicleGasDO.setOperator(operator);
            vehicleGasDO.setFile(file);
            vehicleGasDO.setRemark(remark);
            boolean res = vehicleGasManager.insert(vehicleGasDO);
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
