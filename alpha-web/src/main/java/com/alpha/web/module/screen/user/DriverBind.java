package com.alpha.web.module.screen.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.DriverBindDO;
import com.alpha.domain.DriverDO;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.DriverBindManager;
import com.alpha.query.DriverBindQuery;
import com.alpha.query.DriverQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class DriverBind extends BaseAjaxModule {

    @Resource
    private DriverBindManager driverBindManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("vehicleNO") String vehicleNO, @Param("driverName") String driverName,
                        @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                        Context context) {
        Result<List<DriverBindDO>> result = new Result<List<DriverBindDO>>();
        try {
            DriverBindQuery driverBindQuery = new DriverBindQuery();
            driverBindQuery.setPage(page);
            driverBindQuery.setPageSize(pageSize);
            driverBindQuery.setVehicleNO(vehicleNO);
            driverBindQuery.setDriverName(driverName);
            driverBindQuery.setStartDate(startDate);
            driverBindQuery.setEndDate(endDate);
            List<DriverBindDO> list = driverBindManager.query(driverBindQuery);
            result.setData(list);
        } catch (Exception e) {
            logger.error("DriverBind execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
        }
        print(result);
    }
}
