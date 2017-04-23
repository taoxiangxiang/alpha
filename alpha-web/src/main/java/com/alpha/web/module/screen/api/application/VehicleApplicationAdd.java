package com.alpha.web.module.screen.api.application;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alpha.constans.CalendarUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.manager.VehicleApplicationManager;
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
                        @Param("applicationReason") String applicationReason, @Param("useDate") Date useDate,
                        @Param("predictBackDate") Date predictBackDate, @Param("personNumber") int personNumber,
                        @Param("startPlace") String startPlace, @Param("endPlace") String endPlace,
                        @Param("endPlaceType") String endPlaceType, @Param("contacts") String contacts,
                        @Param("contactsPhone") String contactsPhone, @Param("remark") String remark,
                        @Param("file") String file, Context context) {
        Result<String> result = new Result<String>();
        try {
            SystemAccountDO systemAccountDO = this.getAccount();
            VehicleApplicationDO vehicleApplicationDO = new VehicleApplicationDO();
//        vehicleApplicationDO.setApplicationNO();
            vehicleApplicationDO.setApplicationType(SystemConstant.applicationTypeMap.get(applicationType));
            vehicleApplicationDO.setVehicleType(vehicleType);
            vehicleApplicationDO.setApplicationReason(SystemConstant.applicationReasonMap.get(applicationReason));
            vehicleApplicationDO.setUseDate(CalendarUtil.formatDate(useDate, CalendarUtil.TIME_PATTERN));
            vehicleApplicationDO.setPredictBackDate(CalendarUtil.formatDate(predictBackDate, CalendarUtil.TIME_PATTERN));
            vehicleApplicationDO.setApplicant(systemAccountDO.getName());
            vehicleApplicationDO.setApplicationDepartment(systemAccountDO.getDepartment());
            vehicleApplicationDO.setApplicantPhone(systemAccountDO.getMobilePhone());
            vehicleApplicationDO.setPersonNumber(personNumber);
            vehicleApplicationDO.setStartPlace(startPlace);
            vehicleApplicationDO.setEndPlace(endPlace);
            vehicleApplicationDO.setEndPlaceType(SystemConstant.endPlaceTypeMap.get(endPlaceType));
            vehicleApplicationDO.setContacts(contacts);
            vehicleApplicationDO.setContactsPhone(contactsPhone);
            vehicleApplicationDO.setRemark(remark);
            vehicleApplicationDO.setFile(file);
            if (SystemConstant.END_PLACE_IN_CITY.equals(endPlaceType)) {
                vehicleApplicationDO.setStatus(SystemConstant.VEHICLE_IN_CITY_WAIT_VERIFY);
            } else {
                vehicleApplicationDO.setStatus(SystemConstant.VEHICLE_OUT_OF_CITY_WAIT_FIRST_VERIFY);
            }
            boolean res = vehicleApplicationManager.insert(vehicleApplicationDO);
            if (res) {
                result.setData("申请成功");
            } else {
                result.setErrMsg("申请失败，请重新申请");
            }
        } catch (Exception e) {
            logger.error("VehicleApplication execute catch exception", e);
            result.setErrMsg("系统异常，请重新申请");
        }
        print(result);
    }
}
