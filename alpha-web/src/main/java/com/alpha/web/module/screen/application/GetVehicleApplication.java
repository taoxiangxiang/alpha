package com.alpha.web.module.screen.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.query.VehicleApplicationQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import java.util.List;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/6.
 */
public class GetVehicleApplication extends BaseAjaxModule {

    @Resource
    private VehicleApplicationManager vehicleApplicationManager;

    public void execute(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
                        @Param("status") String status, @Param("id") Integer id, Context context) {
        try {
            VehicleApplicationQuery vehicleApplicationQuery = new VehicleApplicationQuery();
            if (id == null) {
                vehicleApplicationQuery.setStartDate(startDate);
                vehicleApplicationQuery.setEndDate(endDate);
                List<String> statusList = new ArrayList<String>();
                statusList.add(status);
                vehicleApplicationQuery.setStatusList(statusList);
                List<VehicleApplicationDO> list = vehicleApplicationManager.query(vehicleApplicationQuery);
                Result<List<VehicleApplicationDO>> result = new Result<List<VehicleApplicationDO>>(list);
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
            logger.error("GetVehicleApplication execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }
}
