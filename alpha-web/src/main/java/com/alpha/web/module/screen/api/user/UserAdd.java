package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class UserAdd extends BaseAjaxModule {

    @Resource
    private SystemAccountManager systemAccountManager;

    public void execute(@Param("name") String name, @Param("sex") String sex,
                        @Param("citizenId") String citizenId, @Param("birth") String birth,
                        @Param("ethnicGroup") String ethnicGroup, @Param("nativePlace") String nativePlace,
                        @Param("education") String education, @Param("telNumber") String telNumber,
                        @Param("mobilePhone") String mobilePhone, @Param("hireDate") Date hireDate,
                        @Param("mailbox") String mailbox, @Param("address") String address,
                        @Param("position") String position, @Param("department") String department,
                        @Param("picUrl") String picUrl, Context context) {
        Result<String> result = new Result<String>();
        try {
            if (name == null || citizenId == null || citizenId.length() != 18) {
                result.setErrMsg("请正确填写姓名和身份证信息");
                return;
            }
            SystemAccountDO systemAccountDO = new SystemAccountDO();
            systemAccountDO.setName(name);
            systemAccountDO.setNick(name);
            systemAccountDO.setSex(sex);
            systemAccountDO.setCitizenId(citizenId);
            //身份证后六位
            systemAccountDO.setPassword(encoderByMd5(citizenId.substring(12)));
            systemAccountDO.setBirth(birth);
            systemAccountDO.setEthnicGroup(ethnicGroup);
            systemAccountDO.setNativePlace(nativePlace);
            systemAccountDO.setEducation(education);
            systemAccountDO.setTelNumber(telNumber);
            systemAccountDO.setMobilePhone(mobilePhone);
            systemAccountDO.setMailbox(mailbox);
            systemAccountDO.setAddress(address);
            systemAccountDO.setHireDate(hireDate == null ? null : CalendarUtil.formatDate(hireDate, CalendarUtil.DATE_FMT_3));
            systemAccountDO.setPosition(position);
            systemAccountDO.setDepartment(department);
            systemAccountDO.setPicUrl(picUrl);
            systemAccountDO.setStatus(SystemConstant.ACCOUNT_ON_LINE);
            boolean res = systemAccountManager.insert(systemAccountDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("UserAdd execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
        }
        print(result);
    }
}
