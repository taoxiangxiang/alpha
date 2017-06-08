package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleApplicationSumDO;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.query.VehicleApplicationQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/5/7.
 */
public class DepartmentVehicleStatistics extends BaseAjaxModule{

    @Resource
    private VehicleApplicationManager vehicleApplicationManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("departmentName") String departmentName,
                        @Param("startDate") Long startDate, @Param("endDate") Long endDate) {
        PageResult<List<VehicleApplicationSumDO>> result = new PageResult<List<VehicleApplicationSumDO>>();
        try {
            SystemAccountDO curAccountDO = this.getAccount();
            if (curAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!curAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10;
            VehicleApplicationQuery vehicleApplicationQuery = new VehicleApplicationQuery();
            vehicleApplicationQuery.setPage(page);
            vehicleApplicationQuery.setPageSize(pageSize);
            vehicleApplicationQuery.setStartDate(startDate == null ? null : new Date(startDate));
            vehicleApplicationQuery.setEndDate(endDate == null ? null : new Date(endDate));
            vehicleApplicationQuery.setApplicationDepartment(departmentName);
            List<String> statusList = new ArrayList<String>();
            statusList.add(SystemConstant.VEHICLE_VERIFY_PASS);
            vehicleApplicationQuery.setStatusList(statusList);
            List<VehicleApplicationSumDO> list = vehicleApplicationManager.queryGroupByDepartment(vehicleApplicationQuery);
            int number = vehicleApplicationManager.countGroupByDepartment(vehicleApplicationQuery);
            result.setData(list);
            result.setPage(page);
            result.setPageSize(pageSize);
            result.setNumber(number);
            print(result);
        } catch (Exception e) {
            logger.error("DepartmentVehicleStatistics execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
            print(result);
        }
    }
}
