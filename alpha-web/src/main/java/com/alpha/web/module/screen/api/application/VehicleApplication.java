package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.query.VehicleApplicationQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import java.util.List;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/6.
 */
public class VehicleApplication extends BaseAjaxModule {

    @Resource
    private VehicleApplicationManager vehicleApplicationManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                        @Param("status") String status, @Param("id") Integer id, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10;
            VehicleApplicationQuery vehicleApplicationQuery = new VehicleApplicationQuery();
            if (id == null) {
                vehicleApplicationQuery.setPage(page);
                vehicleApplicationQuery.setPageSize(pageSize);
                vehicleApplicationQuery.setStartDate(startDate);
                vehicleApplicationQuery.setEndDate(endDate);
                if (status != null) {
                    List<String> statusList = new ArrayList<String>();
                    statusList.add(status);
                    vehicleApplicationQuery.setStatusList(statusList);
                }
                List<VehicleApplicationDO> list = vehicleApplicationManager.query(vehicleApplicationQuery);
                int number = vehicleApplicationManager.count(vehicleApplicationQuery);
                PageResult<List<VehicleApplicationDO>> result = new PageResult<List<VehicleApplicationDO>>();
                result.setData(list);
                result.setPage(page);
                result.setPageSize(pageSize);
                result.setNumber(number);
                print(result);
            } else {
                Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
                vehicleApplicationQuery.setId(id);
                List<VehicleApplicationDO> list = vehicleApplicationManager.query(vehicleApplicationQuery);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关申请单数据：id=" + id);
                } else {
                    result.setData(list.get(0));
                }
                print(result);
            }
        } catch (Exception e) {
            Result<VehicleApplicationDO> result = new Result<VehicleApplicationDO>();
            logger.error("VehicleApplication execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }
}
