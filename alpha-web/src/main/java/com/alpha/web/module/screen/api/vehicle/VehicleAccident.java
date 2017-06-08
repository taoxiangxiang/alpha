package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleAccidentDO;
import com.alpha.manager.VehicleAccidentManager;
import com.alpha.query.VehicleAccidentQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
public class VehicleAccident extends BaseAjaxModule {

    @Resource
    private VehicleAccidentManager vehicleAccidentManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("vehicleNO") String vehicleNO,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        @Param("id") Integer id, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10;
            SystemAccountDO systemAccountDO = this.getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!systemAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            VehicleAccidentQuery vehicleAccidentQuery = new VehicleAccidentQuery();
            if (id == null) {
                PageResult<List<VehicleAccidentDO>> result = new PageResult<List<VehicleAccidentDO>>();
                vehicleAccidentQuery.setPage(page);
                vehicleAccidentQuery.setPageSize(pageSize);
                vehicleAccidentQuery.setVehicleNO(vehicleNO);
                vehicleAccidentQuery.setStartDate(startDate == null ? null : CalendarUtil.formatDate(new Date(startDate), CalendarUtil.TIME_PATTERN));
                vehicleAccidentQuery.setEndDate(endDate == null ? null : CalendarUtil.formatDate(new Date(endDate), CalendarUtil.TIME_PATTERN));
                List<VehicleAccidentDO> list = vehicleAccidentManager.query(vehicleAccidentQuery);
                int number = vehicleAccidentManager.count(vehicleAccidentQuery);
                result.setData(list);
                result.setPage(page);
                result.setPageSize(pageSize);
                result.setNumber(number);
                result.setData(list);
                print(result);
            } else {
                Result<VehicleAccidentDO> result = new Result<VehicleAccidentDO>();
                vehicleAccidentQuery.setId(id);
                List<VehicleAccidentDO> list = vehicleAccidentManager.query(vehicleAccidentQuery);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关数据");
                } else {
                    result.setData(list.get(0));
                }
                print(result);
            }

        } catch (Exception e) {
            Result<VehicleAccidentDO> result = new Result<VehicleAccidentDO>();
            logger.error("VehicleIllegal execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }
}
