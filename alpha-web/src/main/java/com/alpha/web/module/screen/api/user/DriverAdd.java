package com.alpha.web.module.screen.api.user;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.ParamUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.DriverDO;
import com.alpha.manager.DriverManager;
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

    public void execute(@Param("name") String name, @Param("sex") String sex,
                        @Param("citizenId") String citizenId, @Param("birth") Long birth,
                        @Param("ethnicGroup") String ethnicGroup, @Param("nativePlace") String nativePlace,
                        @Param("education") String education, @Param("mobilePhone") String mobilePhone,
                        @Param("mailbox") String mailbox, @Param("address") String address,
                        @Param("drivingLicense") String drivingLicense, @Param("licenseClass") String licenseClass,
                        @Param("licenseStart") Long licenseStart, @Param("licenseEnd") Long licenseEnd,
                        @Param("offerLicense") String offerLicense, @Param("remark") String remark,
                        @Param("personUrl") String personUrl, @Param("licenseUrl") String licenseUrl,
                        Context context) {
        Result<String> result = new Result<String>();
        try {
            if (StringUtil.isBlank(name)) {
                result.setErrMsg("请填写姓名！");
                print(result);
                return;
            }
            name = name.trim();
            if (StringUtil.isBlank(citizenId) || !ParamUtil.validCitizenId(citizenId.trim())) {
                result.setErrMsg("请填写正确的身份证号！");
                print(result);
                return;
            }
            citizenId = citizenId.trim();
            if (StringUtil.isBlank(mobilePhone) || !ParamUtil.validMobile(mobilePhone.trim())) {
                result.setErrMsg("请填写正确的11位手机号码！");
                print(result);
                return;
            }
            mobilePhone = mobilePhone.trim();
            DriverDO driverDO = new DriverDO();
            driverDO.setName(name);
            driverDO.setSex(sex);
            driverDO.setCitizenId(citizenId);
            driverDO.setBirth(birth == null ? null : CalendarUtil.formatDate(new Date(birth), CalendarUtil.DATE_FMT_3));
            driverDO.setEthnicGroup(ethnicGroup);
            driverDO.setNativePlace(nativePlace);
            driverDO.setEducation(education);
            driverDO.setMobilePhone(mobilePhone);
            driverDO.setMailbox(mailbox);
            driverDO.setAddress(address);
            driverDO.setDrivingLicense(drivingLicense);
            driverDO.setLicenseClass(licenseClass);
            driverDO.setLicenseStart(licenseStart == null ? null : CalendarUtil.formatDate(new Date(licenseStart), CalendarUtil.DATE_FMT_3));
            driverDO.setLicenseEnd(licenseEnd == null ? null : CalendarUtil.formatDate(new Date(licenseEnd), CalendarUtil.DATE_FMT_3));
            driverDO.setOfferLicense(offerLicense);
            driverDO.setRemark(remark);
            driverDO.setPersonUrl(personUrl);
            driverDO.setLicenseUrl(licenseUrl);
            driverDO.setStatus(SystemConstant.DRIVER_CAN_USE);
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
}
