package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.DriverManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class DriverInvalid extends BaseAjaxModule {

    @Resource
    private DriverManager driverManager;

    public void execute(@Param("id") int id,  Context context) {
        Result<String> result = new Result<String>();
        try {
            SystemAccountDO systemAccountDO = this.getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!systemAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            DriverDO driverDO = new DriverDO();
            driverDO.setId(id);
            driverDO.setStatus(SystemConstant.DRIVER_OFF_LINE);
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
