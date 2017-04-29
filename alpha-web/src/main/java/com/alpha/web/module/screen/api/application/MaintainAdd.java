package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.MaintainDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.MaintainManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/26.
 */
public class MaintainAdd extends BaseAjaxModule {

    @Resource
    private MaintainManager maintainManager;

    public void execute(@Param("vehicleNO") String vehicleNO, @Param("team") String team,
                        @Param("predictPickUpDate") String predictPickUpDate, @Param("type") String type,
                        @Param("reason") String reason, @Param("remark") String remark,
                        @Param("file") String file, Context context) {
        Result<String> result = new Result<String>();
        try {
            SystemAccountDO systemAccountDO = this.getAccount();
            MaintainDO maintainDO = new MaintainDO();
            maintainDO.setVehicleNO(vehicleNO);
            maintainDO.setTeam(team);
            maintainDO.setApplicant(systemAccountDO.getName());
            maintainDO.setApplicantPhone(systemAccountDO.getMobilePhone());
            maintainDO.setPredictPickUpDate(predictPickUpDate);
            maintainDO.setType(type);
            maintainDO.setReason(reason);
            maintainDO.setApplicationRemark(remark);
            maintainDO.setFile(file);
            maintainDO.setStatus(SystemConstant.MAINTAIN_WAIT_FIRST_VERIFY);
            boolean res = maintainManager.insert(maintainDO);
            if (res) {
                result.setData("申请成功");
            } else {
                result.setErrMsg("申请失败，请重新申请");
            }
        } catch (Exception e) {
            logger.error("MaintainAdd execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
        }
        print(result);
    }
}
