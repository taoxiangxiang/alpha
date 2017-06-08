package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.query.VehicleApplicationQuery;
import com.alpha.web.common.BaseAjaxModule;
import com.alpha.web.domain.Result;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/6.
 */
public class VehicleApplicationAdd extends BaseAjaxModule {

    @Resource
    private VehicleApplicationManager vehicleApplicationManager;

    public void execute(@Param("applicationType") String applicationType, @Param("vehicleType") String vehicleType,
                        @Param("applicationReason") String applicationReason, @Param("useDate") Long useDate,
                        @Param("predictBackDate") Long predictBackDate, @Param("personNumber") int personNumber,
                        @Param("startPlace") String startPlace, @Param("endPlace") String endPlace,
                        @Param("endPlaceType") String endPlaceType, @Param("contacts") String contacts,
                        @Param("contactsPhone") String contactsPhone, @Param(name="remark", defaultValue="") String remark,
                        @Param(name="file", defaultValue="") String file, @Param("usePerson") String usePerson, Context context) {
        Result<String> result = new Result<String>();
        try {
            file = (file == null ? "" : file);
            remark = (remark == null ? "" : remark);
            SystemAccountDO systemAccountDO = getAccount();
            if (systemAccountDO == null) {
                print(new Result<String>("请先登录系统"));
                return;
            }
            /*权限判断*/
            if (systemAccountDO.isDriver()) {
                print(new Result<String>("您暂无此项功能权限"));
                return;
            }
            VehicleApplicationDO vehicleApplicationDO = new VehicleApplicationDO();
            vehicleApplicationDO.setApplicationType(applicationType);
            vehicleApplicationDO.setVehicleType(vehicleType);
            vehicleApplicationDO.setApplicationReason(applicationReason);
            vehicleApplicationDO.setUseDate(useDate == null ? null : new Date(useDate));
            vehicleApplicationDO.setPredictBackDate(predictBackDate == null ? null : new Date(predictBackDate));
            vehicleApplicationDO.setUsePerson(usePerson);
            vehicleApplicationDO.setApplicantId(systemAccountDO.getId());
            vehicleApplicationDO.setApplicant(systemAccountDO.getName());
            vehicleApplicationDO.setApplicationDepartment(systemAccountDO.getDepartment());
            vehicleApplicationDO.setApplicantPhone(systemAccountDO.getMobilePhone());
            vehicleApplicationDO.setPersonNumber(personNumber);
            vehicleApplicationDO.setStartPlace(startPlace);
            vehicleApplicationDO.setEndPlace(endPlace);
            vehicleApplicationDO.setEndPlaceType(endPlaceType);
            vehicleApplicationDO.setContacts(contacts);
            vehicleApplicationDO.setContactsPhone(contactsPhone);
            vehicleApplicationDO.setRemark(remark);
            vehicleApplicationDO.setFile(file);
            if (SystemConstant.END_PLACE_IN_CITY.equals(endPlaceType)) {
                vehicleApplicationDO.setStatus(SystemConstant.VEHICLE_IN_CITY_WAIT_VERIFY);
            } else {
                vehicleApplicationDO.setStatus(SystemConstant.VEHICLE_OUT_OF_CITY_WAIT_FIRST_VERIFY);
            }
            String checkParamRes = checkParam(vehicleApplicationDO);
            if (!"ok".equals(checkParamRes)) {
                result.setErrMsg(checkParamRes);
                print(result);
                return;
            }
            boolean res = vehicleApplicationManager.insert(vehicleApplicationDO);
            if (res) {
                result.setData("申请成功");
            } else {
                result.setErrMsg("申请失败，请重新申请");
            }
        } catch (Exception e) {
            logger.error("VehicleApplicationAdd execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
        }
        print(result);
    }

    private String checkParam(VehicleApplicationDO vehicleApplicationDO) {
        if (StringUtil.isBlank(vehicleApplicationDO.getApplicationType())) {
            return  "请填写申请类型";
        }
        if (StringUtil.isBlank(vehicleApplicationDO.getVehicleType())) {
            return  "请填写车辆类型";
        }
        if ("应急用车".equals(vehicleApplicationDO.getApplicationType()) && StringUtil.isBlank(vehicleApplicationDO.getApplicationReason())) {
            return  "请填写用车原因";
        }
        if (vehicleApplicationDO.getUseDate() == null) {
            return  "请填写用车时间";
        }
        if (vehicleApplicationDO.getPredictBackDate() == null) {
            return  "请填写预计回车时间";
        }
        if (vehicleApplicationDO.getPersonNumber() <= 0) {
            return  "请填写乘车人数";
        }
        if (StringUtil.isBlank(vehicleApplicationDO.getStartPlace())) {
            return  "请填写出发地点";
        }
        if (StringUtil.isBlank(vehicleApplicationDO.getEndPlace())) {
            return  "请填写目的地点";
        }
        if (StringUtil.isBlank(vehicleApplicationDO.getEndPlaceType()) || !SystemConstant.endPlaceTypeMap.containsValue(vehicleApplicationDO.getEndPlaceType())) {
            return  "请填写目的地点类型";
        }
        if (StringUtil.isBlank(vehicleApplicationDO.getContacts())) {
            return  "请填写联系人";
        }
        if (StringUtil.isBlank(vehicleApplicationDO.getContactsPhone())) {
            return  "请填写联系电话";
        }
        if (StringUtil.isBlank(vehicleApplicationDO.getUsePerson())) {
            return  "请填写车辆使用人";
        }
        return "ok";
    }
}
