package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleUseDO;
import com.alpha.domain.VehicleUseSumDO;
import com.alpha.manager.VehicleUseManager;
import com.alpha.query.VehicleUseQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/5/6.
 */
public class VehicleUseStatistics extends BaseAjaxModule {

    @Resource
    private VehicleUseManager vehicleUseManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("driverName") String driverName,@Param("team") String team,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        Context context) {
        PageResult<List<VehicleUseSumDO>> result = new PageResult<List<VehicleUseSumDO>>();
        try {
            SystemAccountDO curAccountDO = this.getAccount();
            if (curAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!curAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 1000;
            VehicleUseQuery vehicleUseQuery = new VehicleUseQuery();
            vehicleUseQuery.setPage(page);
            vehicleUseQuery.setPageSize(pageSize);
            vehicleUseQuery.setDriverName(driverName);
            vehicleUseQuery.setTeam(team);
            vehicleUseQuery.setStartDate(startDate == null ? null : CalendarUtil.toString(new Date(startDate), CalendarUtil.TIME_PATTERN));
            vehicleUseQuery.setEndDate(endDate == null ? null : CalendarUtil.toString(new Date(endDate), CalendarUtil.TIME_PATTERN));
            int number = vehicleUseManager.countGroupByDriver(vehicleUseQuery);
            List<VehicleUseSumDO> list = vehicleUseManager.queryGroupByDriver(vehicleUseQuery);
            result.setData(list);
            result.setPage(page);
            result.setPageSize(pageSize);
            result.setNumber(number);
            print(result);
        } catch (Exception e) {
            logger.error("VehicleUse execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
            print(result);
        }
    }
}
