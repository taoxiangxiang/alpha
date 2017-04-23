package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.VehicleUseDO;
import com.alpha.manager.VehicleUseManager;
import com.alpha.query.VehicleUseQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;
import sun.jvm.hotspot.debugger.Page;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/19.
 */
public class VehicleUse extends BaseAjaxModule {

    @Resource
    private VehicleUseManager vehicleUseManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("applicationId") Integer applicationId,
                        Context context) {
        PageResult<List<VehicleUseDO>> result = new PageResult<List<VehicleUseDO>>();
        try {
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10;
            VehicleUseQuery vehicleUseQuery = new VehicleUseQuery();
            vehicleUseQuery.setPage(page);
            vehicleUseQuery.setPageSize(pageSize);
            vehicleUseQuery.setApplicationId(applicationId);
            int number = vehicleUseManager.count(vehicleUseQuery);
            List<VehicleUseDO> list = vehicleUseManager.query(vehicleUseQuery);
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