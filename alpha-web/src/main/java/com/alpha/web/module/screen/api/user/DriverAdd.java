package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.ParamUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverDO;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleAccidentDO;
import com.alpha.manager.DriverManager;
import com.alpha.manager.SystemAccountManager;
import com.alpha.query.DriverQuery;
import com.alpha.query.SystemAccountQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/8.
 */
public class DriverAdd extends BaseAjaxModule {

    @Resource
    private DriverManager driverManager;
    @Resource
    private SystemAccountManager systemAccountManager;

    public void execute(@Param("name") String name, @Param("sex") String sex, @Param("team") String team,
                        @Param("citizenId") String citizenId, @Param("birth") Long birth,
                        @Param("ethnicGroup") String ethnicGroup, @Param("nativePlace") String nativePlace,
                        @Param("education") String education, @Param("mobilePhone") String mobilePhone,
                        @Param(name="mailbox", defaultValue="") String mailbox, @Param(name="address", defaultValue="") String address,
                        @Param("drivingLicense") String drivingLicense, @Param("licenseClass") String licenseClass,
                        @Param("licenseStart") Long licenseStart, @Param("licenseEnd") Long licenseEnd,
                        @Param("offerLicense") String offerLicense, @Param(name="remark", defaultValue="") String remark,
                        @Param(name="personUrl", defaultValue="") String personUrl, @Param("licenseUrl") String licenseUrl,
                        Context context) {
        Result<String> result = new Result<String>();
        try {
            remark = (remark == null ? "" : remark);
            mailbox = (mailbox == null ? "" : mailbox);
            address = (address == null ? "" : address);
            personUrl = (personUrl == null ? "" : personUrl);
            SystemAccountDO systemAccountDO = this.getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请登录系统"));
                return;
            }
            if (!systemAccountDO.hasAuth()) {
                print(new Result<String>("您没有该功能权限"));
                return;
            }
            citizenId = citizenId == null ? null : citizenId.trim();
            name = name == null ? null : name.trim();
            mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
            DriverDO driverDO = new DriverDO();
            driverDO.setName(name);
            driverDO.setTeam(team);
            driverDO.setSex(sex);
            driverDO.setCitizenId(citizenId);
            driverDO.setBirth(birth == null ? null : new Date(birth));
            driverDO.setEthnicGroup(ethnicGroup);
            driverDO.setNativePlace(nativePlace);
            driverDO.setEducation(education);
            driverDO.setMobilePhone(mobilePhone);
            driverDO.setMailbox(mailbox);
            driverDO.setAddress(address);
            driverDO.setDrivingLicense(drivingLicense);
            driverDO.setLicenseClass(licenseClass);
            driverDO.setLicenseStart(licenseStart == null ? null : new Date(licenseStart));
            driverDO.setLicenseEnd(licenseEnd == null ? null : new Date(licenseEnd));
            driverDO.setOfferLicense(offerLicense);
            driverDO.setRemark(remark);
            driverDO.setPersonUrl(personUrl);
            driverDO.setLicenseUrl(licenseUrl);
            driverDO.setStatus(SystemConstant.DRIVER_CAN_USE);
            String checkParamRes = checkParam(driverDO);
            if (!"ok".equals(checkParamRes)) {
                result.setErrMsg(checkParamRes);
                print(result);
                return;
            }
            boolean res = driverManager.insert(driverDO);
            if (res) {
                result.setData("操作成功");
            } else {
                result.setErrMsg("操作失败，请重新操作");
            }
        } catch (Exception e) {
            logger.error("DriverUpdate execute catch exception", e);
            result.setErrMsg("系统异常，请重新操作");
        }
        print(result);
    }

    private String checkParam(DriverDO driverDO) {
        if (StringUtil.isBlank(driverDO.getName())) {
            return "请填写姓名";
        }
        if (StringUtil.isBlank(driverDO.getTeam())) {
            return "请设置所属车队";
        }
        if (StringUtil.isBlank(driverDO.getSex())) {
            return "请填写性别";
        }
        if (StringUtil.isBlank(driverDO.getCitizenId()) || !ParamUtil.validCitizenId(driverDO.getCitizenId())) {
            return "请填写正确的身份证号";
        }
        DriverQuery driverQuery = new DriverQuery();
        driverQuery.setCitizenId(driverDO.getCitizenId());
        if (driverManager.count(driverQuery) > 0) {
            return "该身份证号已被他人使用，请核实身份证号";
        }

        SystemAccountQuery systemAccountQuery2 = new SystemAccountQuery();
        systemAccountQuery2.setCitizenId(driverDO.getCitizenId());
        if (systemAccountManager.count(systemAccountQuery2) > 0) {
            return "该身份证号已被他人使用，请核实身份证号";
        }

        if (driverDO.getBirth() == null) {
            return "请填写生日";
        }
//        if (StringUtil.isBlank(driverDO.getEthnicGroup())) {
//            return "请填写民族";
//        }
//        if (StringUtil.isBlank(driverDO.getNativePlace())) {
//            return "请填写籍贯";
//        }
//        if (StringUtil.isBlank(driverDO.getEducation())) {
//            return "请填写学历";
//        }
        if (StringUtil.isBlank(driverDO.getMobilePhone()) || !ParamUtil.validMobile(driverDO.getMobilePhone())) {
            return "请填写正确的11位手机号码";
        }
//        if (StringUtil.isBlank(driverDO.getDrivingLicense())) {
//            return "请填写驾驶证号";
//        }
//        if (StringUtil.isBlank(driverDO.getLicenseClass())) {
//            return "请填写驾驶证类型";
//        }
//        if (driverDO.getLicenseStart() == null) {
//            return "请填写驾照开始日期";
//        }
//        if (driverDO.getLicenseEnd() == null) {
//            return "请填写驾照结束日期";
//        }
//        if (StringUtil.isBlank(driverDO.getOfferLicense())) {
//            return "请填写发证机关";
//        }
        return "ok";
    }
}
