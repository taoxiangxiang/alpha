package com.alpha.web.module.screen.department;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DepartmentDO;
import com.alpha.domain.VehicleDO;
import com.alpha.manager.DepartmentManager;
import com.alpha.query.DepartmentQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;
import com.taobao.util.CalendarUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class DepartmentAdd extends BaseAjaxModule {

    @Resource
    private DepartmentManager departmentManager;

    public void execute(@Param("departmentName") String departmentName,
                        @Param("belongDepartmentName") String belongDepartmentName,
                        @Param("departmentContact") String departmentContact,
                        @Param("departmentContactPhone") String departmentContactPhone,
                        @Param("departmentAddress") String departmentAddress,
                        Context context) {
        Result<String> result = new Result<String>();
        try {
            DepartmentDO departmentDO = new DepartmentDO();
            departmentDO.setDepartmentName(departmentName);
            departmentDO.setBelongDepartmentName(belongDepartmentName);
            departmentDO.setDepartmentContact(departmentContact);
            departmentDO.setDepartmentContactPhone(departmentContactPhone);
            departmentDO.setDepartmentAddress(departmentAddress);
            departmentDO.setStatus(SystemConstant.DEPARTMENT_ON_LINE);
            boolean res = departmentManager.insert(departmentDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("AddVehicle execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
        }
        print(result);
    }
}
