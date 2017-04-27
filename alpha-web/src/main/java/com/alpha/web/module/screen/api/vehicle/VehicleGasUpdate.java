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
public class VehicleGasUpdate extends BaseAjaxModule {

    @Resource
    private VehicleGasManager vehicleGasManager;

    public void execute(@Param("vehicleNO") String vehicleNO, @Param("team") String team,
                        @Param("gasDate") Date gasDate, @Param("gasCardNO") String gasCardNO,
                        @Param("gasAddress") String gasAddress, @Param("price") Double price,
                        @Param("money") Double money, @Param("amount") Double amount,
                        @Param("curMile") Integer curMile, @Param("beforeMile") Integer beforeMile,
                        @Param("payType") String payType, @Param("gasType") String gasType,
                        @Param("operator") String operator,
                        @Param("file") String file, @Param("remark") String remark,
                        @Param("id") Integer id, Context context) {
        Result<String> result = new Result<String>();
        try {
            VehicleGasDO vehicleGasDO = new VehicleGasDO();
            vehicleGasDO.setId(id);
            vehicleGasDO.setVehicleNO(vehicleNO);
            vehicleGasDO.setTeam(team);
            vehicleGasDO.setGasDate(CalendarUtil.formatDate(gasDate, CalendarUtil.TIME_PATTERN));
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
}
