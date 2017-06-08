package com.alpha.web.module.screen.api.department;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DepartmentDO;
import com.alpha.domain.PartnerDepartmentDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.DepartmentManager;
import com.alpha.manager.PartnerDepartmentManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class PartnerDepartmentUpdate extends BaseAjaxModule {

    @Resource
    private PartnerDepartmentManager partnerDepartmentManager;

    public void execute(@Param("departmentName") String departmentName, @Param("type") String type,
                        @Param("address") String address, @Param(name="telNumber", defaultValue="") String telNumber,
                        @Param("contact") String contact, @Param("mobilePhone") String mobilePhone,
                        @Param(name="remark", defaultValue="") String remark,
                        @Param("id") int id, Context context) {
        Result<String> result = new Result<String>();
        try {
            remark = (remark == null ? "" : remark);
            telNumber = (telNumber == null ? "" : telNumber);
            SystemAccountDO curAccountDO = this.getAccount();
            if (curAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!curAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            PartnerDepartmentDO partnerDepartmentDO = new PartnerDepartmentDO();
            partnerDepartmentDO.setId(id);
            partnerDepartmentDO.setDepartmentName(departmentName);
            partnerDepartmentDO.setType(type);
            partnerDepartmentDO.setAddress(address);
            partnerDepartmentDO.setTelNumber(telNumber);
            partnerDepartmentDO.setContact(contact);
            partnerDepartmentDO.setRemark(remark);
            partnerDepartmentDO.setMobilePhone(mobilePhone);
            String checkParamRes = checkParam(partnerDepartmentDO);
            if (!"ok".equals(checkParamRes)) {
                result.setErrMsg(checkParamRes);
                print(result);
                return;
            }
            boolean res = partnerDepartmentManager.update(partnerDepartmentDO);
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

    private String checkParam(PartnerDepartmentDO partnerDepartmentDO) {
        if (StringUtil.isBlank(partnerDepartmentDO.getDepartmentName())) {
            return "请填写部门名称";
        }
        if (StringUtil.isBlank(partnerDepartmentDO.getType())) {
            return "请填写类型";
        }
        if (StringUtil.isBlank(partnerDepartmentDO.getAddress())) {
            return "请填写部门地址";
        }
        if (StringUtil.isBlank(partnerDepartmentDO.getContact())) {
            return "请填部门联系人";
        }
        if (StringUtil.isBlank(partnerDepartmentDO.getMobilePhone())) {
            return "请填写联系人电话";
        }

        return "ok";
    }
}
