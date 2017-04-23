package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.VehicleUseDO;
import com.alpha.manager.VehicleUseManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/16.
 */
public class VehicleUsePoint extends BaseAjaxModule{

    @Resource
    private VehicleUseManager vehicleUseManager;

    public void execute(@Param("id") int id,
                        @Param("point") double point, Context context) {
        Result<String> result = new Result<String>();
        try {
            VehicleUseDO vehicleUseDO = new VehicleUseDO();
            vehicleUseDO.setId(id);
            vehicleUseDO.setPoint(point);
            boolean res = vehicleUseManager.update(vehicleUseDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("VehicleUseUpdate execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
        }
        print(result);
    }
}
