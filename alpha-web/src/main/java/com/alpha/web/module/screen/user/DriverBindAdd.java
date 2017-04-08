package com.alpha.web.module.screen.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverBindDO;
import com.alpha.manager.DriverBindManager;
import com.alpha.query.DriverBindQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class DriverBindAdd extends BaseAjaxModule {

    @Resource
    private DriverBindManager driverBindManager;

    public void execute(@Param("vehicleId") Long vehicleId, @Param("vehicleNO") String vehicleNO,
                        @Param("driverId") Long driverId, @Param("driverName") String driverName,
                        @Param("team") String team, Context context) {
        Result<String> result = new Result<String>();
        try {
            DriverBindDO driverBindDO = new DriverBindDO();
            driverBindDO.setVehicleId(vehicleId);
            driverBindDO.setVehicleNO(vehicleNO);
            driverBindDO.setDriverId(driverId);
            driverBindDO.setDriverName(driverName);
            driverBindDO.setTeam(team);
            driverBindDO.setBindDate(new Date());
            driverBindDO.setStatus(SystemConstant.DRIVER_BIND_VEHICLE);
            boolean res = driverBindManager.insert(driverBindDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("DriverBindAdd execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
        }
        print(result);
    }
}
