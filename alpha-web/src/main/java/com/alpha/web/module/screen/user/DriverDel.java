package com.alpha.web.module.screen.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverDO;
import com.alpha.manager.DriverManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class DriverDel extends BaseAjaxModule {

    @Resource
    private DriverManager driverManager;

    public void execute(@Param("id") int id, Context context) {
        Result<String> result = new Result<String>();
        try {
            DriverDO driverDO = new DriverDO();
            driverDO.setId(id);
            driverDO.setStatus(SystemConstant.DRIVER_DELETE);
            boolean res = driverManager.update(driverDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("InvalidVehicle execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
        }
        print(result);
    }
}
