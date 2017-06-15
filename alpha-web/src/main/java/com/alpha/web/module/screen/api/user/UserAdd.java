package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.ParamUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.SystemAccountQuery;
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

    public void execute(@Param("name") String name, @Param(name="sex", defaultValue="") String sex, @Param("authType") String authType,
                        @Param("nick") String nick, @Param("citizenId") String citizenId, @Param("birth") Long birth,
                        @Param("ethnicGroup") String ethnicGroup, @Param("nativePlace") String nativePlace,
                        @Param("education") String education, @Param(name="telNumber", defaultValue="") String telNumber,
                        @Param("mobilePhone") String mobilePhone, @Param(name="hireDate") Long hireDate,
                        @Param(name="mailbox", defaultValue="") String mailbox, @Param(name="address", defaultValue="") String address,
                        @Param("position") String position, @Param("department") String department,
                        @Param(name="picUrl", defaultValue="") String picUrl, Context context) {
        Result<String> result = new Result<String>();
        try {
            sex = (sex == null ? "" : sex);
            telNumber = (telNumber == null ? "" : telNumber);
            mailbox = (mailbox == null ? "" : mailbox);
            address = (address == null ? "" : address);
            picUrl = (picUrl == null ? "" : picUrl);
            SystemAccountDO curAccountDO = this.getAccount();
            if (curAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!curAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            name = (name == null ? null : name.trim());
            if (StringUtil.isEmpty(name)) {
                result.setErrMsg("请填写姓名！");
                print(result);
                return;
            }
            citizenId = citizenId == null ? null : citizenId.trim();
            if (StringUtil.isEmpty(citizenId) || !ParamUtil.validCitizenId(citizenId)) {
                result.setErrMsg("请填写正确的身份证号！");
                print(result);
                return;
            }
            mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
            if (StringUtil.isEmpty(mobilePhone) || !ParamUtil.validMobile(mobilePhone)) {
                result.setErrMsg("请填写正确的11位手机号码！");
                print(result);
                return;
            }
            SystemAccountDO systemAccountDO = new SystemAccountDO();
            systemAccountDO.setName(name);
            systemAccountDO.setNick(StringUtil.isBlank(nick) ? name : nick);
            systemAccountDO.setSex(sex);
            systemAccountDO.setCitizenId(citizenId);
            //身份证后六位
            systemAccountDO.setPassword(encoderByMd5(citizenId.substring(12)));
            systemAccountDO.setBirth(birth == null ? null : new Date(birth));
            systemAccountDO.setEthnicGroup(ethnicGroup);
            systemAccountDO.setNativePlace(nativePlace);
            systemAccountDO.setEducation(education);
            systemAccountDO.setTelNumber(telNumber);
            systemAccountDO.setMobilePhone(mobilePhone);
            systemAccountDO.setMailbox(mailbox);
            systemAccountDO.setAddress(address);
            systemAccountDO.setHireDate(hireDate == null ? null : new Date(hireDate));
            systemAccountDO.setPosition(position);
            systemAccountDO.setDepartment(department);
            systemAccountDO.setAuthType(authType);
            systemAccountDO.setPicUrl(picUrl);
            systemAccountDO.setStatus(SystemConstant.ACCOUNT_ON_LINE);
            systemAccountDO.setType(hasAuth(authType) ? SystemConstant.USER_TYPE_HAS_AUTH : SystemConstant.USER_TYPE_NO_AUTH);
            String checkParamRes = checkParam(systemAccountDO);
            if (!"ok".equals(checkParamRes)) {
                result.setErrMsg(checkParamRes);
                print(result);
                return;
            }
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

    private String checkParam(SystemAccountDO systemAccountDO) {
        if (StringUtil.isBlank(systemAccountDO.getName())) {
            return "请填写姓名";
        }
        if (StringUtil.isBlank(systemAccountDO.getSex())) {
            return "请填写性别";
        }
        if (StringUtil.isBlank(systemAccountDO.getNick())) {
            return "请填写昵称【昵称用于登录时使用的用户名】";
        }
        if (systemAccountManager.queryByNick(systemAccountDO.getNick()) != null) {
            return "该昵称已被他人使用，请更换昵称";
        }
        if (StringUtil.isBlank(systemAccountDO.getCitizenId()) || !ParamUtil.validCitizenId(systemAccountDO.getCitizenId())) {
            return "请填写正确的身份证号";
        }
        if (systemAccountManager.queryByCitizenId(systemAccountDO.getCitizenId()) != null) {
            return "该身份证号已被他人使用，请核实身份证号";
        }
        if (systemAccountDO.getBirth() == null) {
            return "请填写生日";
        }
        if (StringUtil.isBlank(systemAccountDO.getEthnicGroup())) {
            return "请填写民族";
        }
        if (StringUtil.isBlank(systemAccountDO.getNativePlace())) {
            return "请填写籍贯";
        }
        if (StringUtil.isBlank(systemAccountDO.getEducation())) {
            return "请填写学历";
        }
        if (StringUtil.isBlank(systemAccountDO.getMobilePhone()) || !ParamUtil.validMobile(systemAccountDO.getMobilePhone())) {
            return "请填写正确的11位手机号码";
        }
        if (StringUtil.isBlank(systemAccountDO.getPosition())) {
            return "请填写职位";
        }
        if (StringUtil.isBlank(systemAccountDO.getDepartment())) {
            return "请填写部门";
        }
        if (StringUtil.isBlank(systemAccountDO.getAuthType())) {
            return "请填写拥有权限";
        }
        return "ok";
    }


}
