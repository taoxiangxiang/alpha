package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverDO;
import com.alpha.domain.LeaveDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleAccidentDO;
import com.alpha.manager.DriverManager;
import com.alpha.manager.LeaveManager;
import com.alpha.query.DriverQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/29.
 */
public class LeaveAdd extends BaseAjaxModule {

    @Resource
    private LeaveManager leaveManager;
    @Resource
    private DriverManager driverManager;

    public void execute(@Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("reason") String reason, @Param(name="remark", defaultValue="") String remark,
                        @Param(name="file", defaultValue="") String file,
                        Context context) {
        Result<String> result = new Result<String>();
        try {
            remark = (remark == null ? "" : remark);
            file = (file == null ? "" : file);
            SystemAccountDO systemAccountDO = this.getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!systemAccountDO.hasAuth() && !systemAccountDO.isDriver()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            DriverQuery driverQuery = new DriverQuery();
            driverQuery.setCitizenId(systemAccountDO.getCitizenId());
            List<DriverDO> driverDOList = driverManager.query(driverQuery);
            if (driverDOList == null || driverDOList.size() == 0) {
                result.setErrMsg("您不是系统内部司机，不能请假");
                print(result);
                return;
            }
            DriverDO driverDO = driverDOList.get(0);
            LeaveDO leaveDO = new LeaveDO();
            leaveDO.setDriverId(driverDO.getId());
            leaveDO.setDriverName(driverDO.getName());
            leaveDO.setMobilePhone(driverDO.getMobilePhone());
            leaveDO.setTeam(driverDO.getTeam());
            leaveDO.setStartDate(startDate == null ? null : new Date(startDate));
            leaveDO.setEndDate(endDate == null ? null : new Date(endDate));
            leaveDO.setReason(reason);
            leaveDO.setRemark(remark);
            leaveDO.setFile(file);
            leaveDO.setStatus(SystemConstant.LEAVE_WAIT_FIRST_VERIFY);
            String checkParamRes = checkParam(leaveDO);
            if (!"ok".equals(checkParamRes)) {
                result.setErrMsg(checkParamRes);
                print(result);
                return;
            }
            boolean res = leaveManager.insert(leaveDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("DriverUpdate execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
        }
        print(result);
    }

    private String checkParam(LeaveDO leaveDO) {
        if (leaveDO.getStartDate() == null) {
            return "请填写开始时间";
        }
        if (leaveDO.getEndDate() == null) {
            return "请填写结束时间";
        }
        if (StringUtil.isBlank(leaveDO.getReason())) {
            return "请填写原因";
        }
        return "ok";
    }
}
