package com.alpha.web.module.screen.api.department;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DepartmentDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.DepartmentManager;
import com.alpha.query.DepartmentQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class Department extends BaseAjaxModule {

    @Resource
    private DepartmentManager departmentManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("belongDepartmentName") String belongDepartmentName,
                        @Param("departmentName") String departmentName, @Param("id") Integer id,
                        Context context) {
        try {
            logger.info("1");
            page = page > 0 ? page : 1;
            pageSize = pageSize > 0 ? pageSize : 10;
            SystemAccountDO curAccountDO = this.getAccount();
            if (curAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            DepartmentQuery departmentQuery = new DepartmentQuery();
            if (id == null) {
                PageResult<List<DepartmentDO>> result = new PageResult<List<DepartmentDO>>();
                departmentQuery.setPage(page);
                departmentQuery.setPageSize(pageSize);
                departmentQuery.setDepartmentName(departmentName);
                departmentQuery.setBelongDepartmentName(belongDepartmentName);
                List<String> statusList = new ArrayList<String>();
                statusList.add(SystemConstant.DEPARTMENT_ON_LINE);
                departmentQuery.setStatusList(statusList);
                List<DepartmentDO> list = departmentManager.query(departmentQuery);
                if (list != null) {
                    for (DepartmentDO departmentDO : list) {
                        departmentDO.setStatus(SystemConstant.departmentStatusMap.get(departmentDO.getStatus()));
                    }
                }
                int number = departmentManager.count(departmentQuery);
                result.setData(list);
                result.setPage(page);
                result.setPageSize(pageSize);
                result.setNumber(number);
                print(result);
            } else {
                Result<DepartmentDO> result = new Result<DepartmentDO>();
                departmentQuery.setId(id);
                List<DepartmentDO> list = departmentManager.query(departmentQuery);
                if (list == null || list.size() == 0) {
                    result.setErrMsg("无相关数据");
                } else {
                    DepartmentDO departmentDO = list.get(0);
                    departmentDO.setStatus(SystemConstant.departmentStatusMap.get(departmentDO.getStatus()));
                    result.setData(departmentDO);
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
