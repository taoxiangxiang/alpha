package com.alpha.web.module.screen.api.vehicle;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.VehicleCheckDO;
import com.alpha.manager.VehicleCheckManager;
import com.alpha.query.VehicleCheckQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/27.
 */
public class VehicleCheck extends BaseAjaxModule {

    @Resource
    private VehicleCheckManager vehicleCheckManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("vehicleNO") String vehicleNO,
                        @Param("id") Integer id, Context context) {
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10;
            VehicleCheckQuery vehicleCheckQuery = new VehicleCheckQuery();
            if (id == null) {
                PageResult<List<VehicleCheckDO>> result = new PageResult<List<VehicleCheckDO>>();
                vehicleCheckQuery.setPage(page);
                vehicleCheckQuery.setPageSize(pageSize);
                vehicleCheckQuery.setVehicleNO(vehicleNO);
                List<VehicleCheckDO> list = vehicleCheckManager.query(vehicleCheckQuery);
                int number = vehicleCheckManager.count(vehicleCheckQuery);
                result.setData(list);
                result.setPage(page);
                result.setPageSize(pageSize);
                result.setNumber(number);
                result.setData(list);
                print(result);
            } else {
                Result<VehicleCheckDO> result = new Result<VehicleCheckDO>();
                vehicleCheckQuery.setId(id);
                List<VehicleCheckDO> list = vehicleCheckManager.query(vehicleCheckQuery);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关数据");
                } else {
                    result.setData(list.get(0));
                }
                print(result);
            }

        } catch (Exception e) {
            Result<VehicleCheckDO> result = new Result<VehicleCheckDO>();
            logger.error("VehicleCheck execute catch exception", e);
            result.setErrMsg("系统异常，请重试");
            print(result);
        }
    }
}