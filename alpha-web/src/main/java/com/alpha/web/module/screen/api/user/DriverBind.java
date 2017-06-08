package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverBindDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.DriverBindManager;
import com.alpha.query.DriverBindQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
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
                        @Param("status") String status,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate,
                        Context context) {
        PageResult<List<DriverBindDO>> result = new PageResult<List<DriverBindDO>>();
        try {
            SystemAccountDO systemAccountDO = this.getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10;
            DriverBindQuery driverBindQuery = new DriverBindQuery();
            driverBindQuery.setPage(page);
            driverBindQuery.setPageSize(pageSize);
            driverBindQuery.setVehicleNO(vehicleNO);
            driverBindQuery.setStatus(status);
            driverBindQuery.setDriverName(driverName);
            driverBindQuery.setStartDate(startDate == null ? null : new Date(startDate));
            driverBindQuery.setEndDate(endDate == null ? null : new Date(endDate));
            List<DriverBindDO> list = driverBindManager.query(driverBindQuery);
            if (list != null) {
                for (DriverBindDO driverBindDO : list) {
                    driverBindDO.setStatus(SystemConstant.driverBindStatusMap.get(driverBindDO.getStatus()));
                }
            }
            int number = driverBindManager.count(driverBindQuery);
            result.setPage(page);
            result.setPageSize(pageSize);
            result.setNumber(number);
            result.setData(list);
        } catch (Exception e) {
            logger.error("DriverBind execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
        }
        print(result);
    }
}
