package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.VehicleManager;
import com.alpha.query.VehicleQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class Vehicle extends BaseAjaxModule {

    @Resource
    private VehicleManager vehicleManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("vehicleNO") String vehicleNO, @Param("team") String team,
                        @Param("id") Integer id, Context context) {
        try {
            VehicleQuery vehicleQuery = new VehicleQuery();
            if (id == null) {
                PageResult<List<VehicleDO>> result = new PageResult<List<VehicleDO>>();
                vehicleQuery.setPage(page);
                vehicleQuery.setPageSize(pageSize);
                vehicleQuery.setVehicleNO(vehicleNO);
                vehicleQuery.setTeam(team);
                List<VehicleDO> list = vehicleManager.query(vehicleQuery);
                int number = vehicleManager.count(vehicleQuery);
                result.setData(list);
                result.setPage(page);
                result.setPageSize(pageSize);
                result.setNumber(number);
                result.setData(list);
                print(result);
            } else {
                Result<VehicleDO> result = new Result<VehicleDO>();
                vehicleQuery.setId(id);
                List<VehicleDO> list = vehicleManager.query(vehicleQuery);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关数据");
                } else {
                    result.setData(list.get(0));
                }
                print(result);
            }

        } catch (Exception e) {
            Result<VehicleDO> result = new Result<VehicleDO>();
            logger.error("Vehicle execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }
}
