package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.VehicleIllegalDO;
import com.alpha.domain.VehicleIllegalSumDO;
import com.alpha.manager.VehicleIllegalManager;
import com.alpha.query.VehicleIllegalQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
public class VehicleIllegalStatistics extends BaseAjaxModule {

    @Resource
    private VehicleIllegalManager vehicleIllegalManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("vehicleNO") String vehicleNO,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10;
            VehicleIllegalQuery vehicleIllegalQuery = new VehicleIllegalQuery();
            PageResult<List<VehicleIllegalSumDO>> result = new PageResult<List<VehicleIllegalSumDO>>();
            vehicleIllegalQuery.setPage(page);
            vehicleIllegalQuery.setPageSize(pageSize);
            vehicleIllegalQuery.setVehicleNO(vehicleNO);
            vehicleIllegalQuery.setStartDate(startDate == null ? null : CalendarUtil.formatDate(new Date(startDate), CalendarUtil.TIME_PATTERN));
            vehicleIllegalQuery.setEndDate(endDate == null ? null : CalendarUtil.formatDate(new Date(endDate), CalendarUtil.TIME_PATTERN));
            List<VehicleIllegalSumDO> list = vehicleIllegalManager.queryGroupByVehicle(vehicleIllegalQuery);
            int number = vehicleIllegalManager.countGroupByVehicle(vehicleIllegalQuery);
            result.setData(list);
            result.setPage(page);
            result.setPageSize(pageSize);
            result.setNumber(number);
            result.setData(list);
            print(result);
        } catch (Exception e) {
            Result<VehicleIllegalDO> result = new Result<VehicleIllegalDO>();
            logger.error("VehicleIllegal execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }
}
