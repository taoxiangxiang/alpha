package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleUseDO;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.manager.VehicleUseManager;
import com.alpha.query.VehicleUseQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by taoxiang on 2017/4/19.
 */
public class VehicleUse extends BaseAjaxModule {

    @Resource
    private VehicleUseManager vehicleUseManager;
    @Resource
    private VehicleApplicationManager vehicleApplicationManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("applicationId") String applicationId, @Param("vehicleNO") String vehicleNO,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("alreadyCheck") Boolean alreadyCheck,
                        @Param("team") String team, Context context) {
        PageResult<List<VehicleUseDO>> result = new PageResult<List<VehicleUseDO>>();
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 1000;
            SystemAccountDO systemAccountDO = getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请先登录系统"));
                return;
            }
            VehicleUseQuery vehicleUseQuery = new VehicleUseQuery();
            vehicleUseQuery.setPage(page);
            vehicleUseQuery.setPageSize(pageSize);
            vehicleUseQuery.setApplicationId(applicationId == null ? null :
                    (applicationId.startsWith("YC") ? Integer.valueOf(applicationId.substring(6)) : Integer.valueOf(applicationId)));
            /*权限判断*/
            if (systemAccountDO.isDriver()) {
                vehicleUseQuery.setDriverId(systemAccountDO.getDriverId());
            }
            if (systemAccountDO.hasNOAuth()) {
                vehicleUseQuery.setApplicationIdList(vehicleApplicationManager.queryByApplicantId(systemAccountDO.getId()));
            }
            vehicleUseQuery.setStartDate(startDate == null ? null : CalendarUtil.toString(new Date(startDate), CalendarUtil.TIME_PATTERN));
            vehicleUseQuery.setEndDate(endDate == null ? null : CalendarUtil.toString(new Date(endDate), CalendarUtil.TIME_PATTERN));
            vehicleUseQuery.setTeam(team);
            vehicleUseQuery.setVehicleNO(vehicleNO);
            vehicleUseQuery.setAlreadyCheck(alreadyCheck);
            int number = vehicleUseManager.count(vehicleUseQuery);
            List<VehicleUseDO> list = vehicleUseManager.query(vehicleUseQuery);
            result.setData(list);
            result.setPage(page);
            result.setPageSize(pageSize);
            result.setNumber(number);
            print(result);
        } catch (Exception e) {
            logger.error("VehicleUse execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }



}
