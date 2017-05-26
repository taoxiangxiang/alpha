package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.VehicleGasDO;
import com.alpha.manager.VehicleGasManager;
import com.alpha.query.VehicleGasQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;
import lombok.Data;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
public class VehicleGas extends BaseAjaxModule {

    @Resource
    private VehicleGasManager vehicleGasManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("vehicleNO") String vehicleNO,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("id") Integer id, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10;
            VehicleGasQuery vehicleGasQuery = new VehicleGasQuery();
            if (id == null) {
                PageResult<List<VehicleGasDO>> result = new PageResult<List<VehicleGasDO>>();
                vehicleGasQuery.setPage(page);
                vehicleGasQuery.setPageSize(pageSize);
                vehicleGasQuery.setVehicleNO(vehicleNO);
                vehicleGasQuery.setStartDate(startDate == null ? null : CalendarUtil.formatDate(new Date(startDate), CalendarUtil.TIME_PATTERN));
                vehicleGasQuery.setEndDate(endDate == null ? null : CalendarUtil.formatDate(new Date(endDate), CalendarUtil.TIME_PATTERN));
                List<VehicleGasDO> list = vehicleGasManager.query(vehicleGasQuery);
                int number = vehicleGasManager.count(vehicleGasQuery);
                result.setData(list);
                result.setPage(page);
                result.setPageSize(pageSize);
                result.setNumber(number);
                result.setData(list);
                print(result);
            } else {
                Result<VehicleGasDO> result = new Result<VehicleGasDO>();
                vehicleGasQuery.setId(id);
                List<VehicleGasDO> list = vehicleGasManager.query(vehicleGasQuery);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关数据");
                } else {
                    result.setData(list.get(0));
                }
                print(result);
            }

        } catch (Exception e) {
            Result<VehicleGasDO> result = new Result<VehicleGasDO>();
            logger.error("VehicleGas execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }
}
