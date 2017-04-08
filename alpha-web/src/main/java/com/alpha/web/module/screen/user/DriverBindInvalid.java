package com.alpha.web.module.screen.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverBindDO;
import com.alpha.manager.DriverBindManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class DriverBindInvalid extends BaseAjaxModule {

    @Resource
    private DriverBindManager driverBindManager;

    public void execute(@Param("id") long id, Context context) {
        Result<String> result = new Result<String>();
        try {
            DriverBindDO driverBindDO = new DriverBindDO();
            driverBindDO.setId(id);
            driverBindDO.setStatus(SystemConstant.DRIVER_UNBIND_VEHICLE);
            boolean res = driverBindManager.update(driverBindDO);
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
