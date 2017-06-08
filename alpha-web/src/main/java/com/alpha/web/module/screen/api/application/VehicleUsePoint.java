package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleUseDO;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.manager.VehicleUseManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/16.
 */
public class VehicleUsePoint extends BaseAjaxModule{

    @Resource
    private VehicleUseManager vehicleUseManager;
    @Resource
    private VehicleApplicationManager vehicleApplicationManager;

    public void execute(@Param("id") int id,
                        @Param("point") double point, Context context) {
        Result<String> result = new Result<String>();
        try {
            SystemAccountDO systemAccountDO = getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请先登录系统"));
                return;
            }
            if (point <= 0) {
                result.setErrMsg("评分至少大于0");
                print(result);
                return;
            }
            VehicleUseDO vehicleUseDOInDB = vehicleUseManager.queryById(id);
            if (vehicleUseDOInDB == null) {
                result.setErrMsg("系统没有查询到对应的出车单");
                print(result);
                return;
            }
            List<Integer> applicationIdList = vehicleApplicationManager.queryByApplicantId(systemAccountDO.getId());
            if (!systemAccountDO.hasAuth() && !applicationIdList.contains(vehicleUseDOInDB.getApplicationId())) {
                result.setErrMsg("这不是您的回车登记单");
                print(result);
                return;
            }
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
