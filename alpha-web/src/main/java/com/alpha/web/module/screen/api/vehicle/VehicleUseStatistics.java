package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.VehicleUseDO;
import com.alpha.domain.VehicleUseSumDO;
import com.alpha.manager.VehicleUseManager;
import com.alpha.query.VehicleUseQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;

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
                        @Param("driverName") String driverName,
                        @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                        Context context) {
        PageResult<List<VehicleUseSumDO>> result = new PageResult<List<VehicleUseSumDO>>();
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10;
            VehicleUseQuery vehicleUseQuery = new VehicleUseQuery();
            vehicleUseQuery.setPage(page);
            vehicleUseQuery.setPageSize(pageSize);
            vehicleUseQuery.setDriverName(driverName);
            vehicleUseQuery.setStartDate(CalendarUtil.toString(startDate, CalendarUtil.TIME_PATTERN));
            vehicleUseQuery.setEndDate(CalendarUtil.toString(endDate, CalendarUtil.TIME_PATTERN));
            int number = vehicleUseManager.countGroupByDriver(vehicleUseQuery);
            List<VehicleUseSumDO> list = vehicleUseManager.queryGroupByDriver(vehicleUseQuery);
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
