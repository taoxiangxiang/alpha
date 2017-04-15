package com.alpha.web.module.screen.api.department;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.domain.DepartmentDO;
import com.alpha.manager.DepartmentManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class DepartmentUpdate extends BaseAjaxModule {

    @Resource
    private DepartmentManager departmentManager;

    public void execute(@Param("departmentName") String departmentName,
                        @Param("belongDepartmentName") String belongDepartmentName,
                        @Param("departmentContact") String departmentContact,
                        @Param("departmentContactPhone") String departmentContactPhone,
                        @Param("departmentAddress") String departmentAddress,
                        @Param("id") int id,
                        Context context) {
        Result<String> result = new Result<String>();
        try {
            DepartmentDO departmentDO = new DepartmentDO();
            departmentDO.setId(id);
            departmentDO.setDepartmentName(departmentName);
            departmentDO.setBelongDepartmentName(belongDepartmentName);
            departmentDO.setDepartmentContact(departmentContact);
            departmentDO.setDepartmentContactPhone(departmentContactPhone);
            departmentDO.setDepartmentAddress(departmentAddress);
            boolean res = departmentManager.update(departmentDO);
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
