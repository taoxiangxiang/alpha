package com.alpha.web.module.screen.api.department;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DepartmentDO;
import com.alpha.domain.PartnerDepartmentDO;
import com.alpha.manager.DepartmentManager;
import com.alpha.manager.PartnerDepartmentManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class PartnerDepartmentAdd extends BaseAjaxModule {

    @Resource
    private PartnerDepartmentManager partnerDepartmentManager;

    public void execute(@Param("departmentName") String departmentName, @Param("type") String type,
                        @Param("address") String address, @Param("telNumber") String telNumber,
                        @Param("contact") String contact, @Param("mobilePhone") String mobilePhone,
                        @Param("remark") String remark, Context context) {
        Result<String> result = new Result<String>();
        try {
            PartnerDepartmentDO partnerDepartmentDO = new PartnerDepartmentDO();
            partnerDepartmentDO.setDepartmentName(departmentName);
            partnerDepartmentDO.setType(type);
            partnerDepartmentDO.setAddress(address);
            partnerDepartmentDO.setTelNumber(telNumber);
            partnerDepartmentDO.setContact(contact);
            partnerDepartmentDO.setRemark(remark);
            partnerDepartmentDO.setMobilePhone(mobilePhone);
            partnerDepartmentDO.setStatus(SystemConstant.DEPARTMENT_ON_LINE);
            boolean res = partnerDepartmentManager.insert(partnerDepartmentDO);
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
