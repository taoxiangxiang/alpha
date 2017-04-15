package com.alpha.web.module.screen.api.department;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.DepartmentDO;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.DepartmentManager;
import com.alpha.query.DepartmentQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.PageResult;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class Department extends BaseAjaxModule {

    @Resource
    private DepartmentManager departmentManager;

    public void execute(@Param("page") int page, @Param("pageSize") int pageSize,
                        @Param("departmentName") String departmentName, @Param("id") Integer id,
                        Context context) {
        try {
            DepartmentQuery departmentQuery = new DepartmentQuery();
            if (id == null) {
                PageResult<List<DepartmentDO>> result = new PageResult<List<DepartmentDO>>();
                departmentQuery.setPage(page);
                departmentQuery.setPageSize(pageSize);
                departmentQuery.setDepartmentName(departmentName);
                List<DepartmentDO> list = departmentManager.query(departmentQuery);
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
