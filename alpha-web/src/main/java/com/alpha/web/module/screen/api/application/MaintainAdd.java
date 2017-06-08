package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.MaintainDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.MaintainManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/26.
 */
public class MaintainAdd extends BaseAjaxModule {

    @Resource
    private MaintainManager maintainManager;

    public void execute(@Param("vehicleNO") String vehicleNO, @Param("team") String team,
                        @Param("predictPickUpDate") Long predictPickUpDate, @Param("type") String type,
                        @Param("reason") String reason, @Param(name="remark", defaultValue="") String remark,
                        @Param(name="file", defaultValue="") String file, Context context) {
        Result<String> result = new Result<String>();
        try {
            remark = (remark == null ? "" : remark);
            file = (file == null ? "" : file);
            SystemAccountDO systemAccountDO = this.getAccount();
            if (systemAccountDO == null) {
                result.setErrMsg("请登录系统");
                print(result);
                return;
            }
            if (!systemAccountDO.hasAuth() && !systemAccountDO.isDriver()) {
                result.setErrMsg("您没有维保申请功能权限");
                print(result);
                return;
            }
            MaintainDO maintainDO = new MaintainDO();
            maintainDO.setVehicleNO(vehicleNO);
            maintainDO.setTeam(team);
            maintainDO.setApplicantId(systemAccountDO.getId());
            maintainDO.setApplicant(systemAccountDO.getName());
            maintainDO.setApplicantPhone(systemAccountDO.getMobilePhone());
            maintainDO.setPredictPickUpDate(predictPickUpDate == null ? null : new Date(predictPickUpDate));
            maintainDO.setType(type);
            maintainDO.setReason(reason);
            maintainDO.setApplicationRemark(remark);
            maintainDO.setFile(file);
            maintainDO.setStatus(SystemConstant.MAINTAIN_WAIT_FIRST_VERIFY);
            String checkParamRes = checkParam(maintainDO);
            if (!"ok".equals(checkParamRes)) {
                result.setErrMsg(checkParamRes);
                print(result);
                return;
            }
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

    private String checkParam(MaintainDO maintainDO) {
        if (StringUtil.isBlank(maintainDO.getVehicleNO())) {
            return "请填写车牌号";
        }
        if (StringUtil.isBlank(maintainDO.getTeam())) {
            return "请设置车牌对应的车队";
        }
        if (maintainDO.getPredictPickUpDate() == null) {
            return "请填写预计取车时间";
        }
        if (StringUtil.isBlank(maintainDO.getType())) {
            return "请填写维保类别";
        }
        if (StringUtil.isBlank(maintainDO.getReason())) {
            return "请填写申请原因";
        }
        return "ok";
    }
}
