package com.alpha.manager.impl;

import com.alibaba.citrus.util.CollectionUtil;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.dao.VerifyRecordDao;
import com.alpha.domain.SystemAccountDO;
import com.alpha.domain.VehicleApplicationDO;
import com.alpha.domain.VerifyRecordDO;
import com.alpha.manager.SystemAccountManager;
import com.alpha.manager.VehicleApplicationManager;
import com.alpha.manager.VerifyRecordManager;
import com.alpha.query.SystemAccountQuery;
import com.alpha.query.VehicleApplicationQuery;
import com.alpha.query.VerifyRecordQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/6.
 */
@Component("verifyRecordManager")
public class VerifyRecordManagerImpl implements VerifyRecordManager {

    @Resource
    private VerifyRecordDao verifyRecordDao;
    @Resource
    private VehicleApplicationManager vehicleApplicationManager;
    @Resource
    private SystemAccountManager systemAccountManager;

    @Override
    @Transactional
    public String verify(VerifyRecordDO verifyRecordDO) {
        /**
         * 查询审核人权限
         */
        SystemAccountQuery systemAccountQuery = new SystemAccountQuery();
        systemAccountQuery.setId(verifyRecordDO.getVerifyId());
        List<SystemAccountDO> systemAccountDOList = systemAccountManager.query(systemAccountQuery);
        if (CollectionUtils.isEmpty(systemAccountDOList)) {
            return "您不在审核角色系统中";
        }
        String authType = systemAccountDOList.get(0).getAuthType();
        if (authType == null || authType.trim().length() == 0) {
            return "您在系统中没有审核权限";
        }
        String result = "";
        /**
         * 车辆申请审核
         */
        if (SystemConstant.VERIFY_EVENT_VEHICLE_APPLICATION.equals(verifyRecordDO.getApplicationEvent())) {
            result = verifyVehicleApplication(authType, verifyRecordDO);
        }
        return "true".equals(result) && verifyRecordDao.insert(verifyRecordDO) ? "true" : "系统异常，请稍后再试";
    }

    private String verifyVehicleApplication(String authType, VerifyRecordDO verifyRecordDO) {
        VehicleApplicationQuery vehicleApplicationQuery = new VehicleApplicationQuery();
        vehicleApplicationQuery.setId(verifyRecordDO.getApplicationId());
        List<VehicleApplicationDO> vehicleApplicationDOList = vehicleApplicationManager.query(vehicleApplicationQuery);
        if (CollectionUtils.isEmpty(vehicleApplicationDOList)) {
            return "系统中不存在该车辆申请单";
        }
        VehicleApplicationDO vehicleApplicationDO = vehicleApplicationDOList.get(0);
        String status = vehicleApplicationDO.getStatus();
        /**
         * 市区内单子审核
         */
        if (SystemConstant.VEHICLE_IN_CITY_WAIT_VERIFY.equals(status) && authType.contains(SystemConstant.AUTH_TYPE_VEHICLE_IN_CITY_VERIFY)) {
            vehicleApplicationDO.setStatus(SystemConstant.VEHICLE_VERIFY_PASS.equals(verifyRecordDO.getResult()) ?
                    SystemConstant.VEHICLE_VERIFY_PASS : SystemConstant.VEHICLE_VERIFY_REJECT);
            return vehicleApplicationManager.update(vehicleApplicationDO) ? "true" : "系统异常，请稍后再试";
        }
        /**
         * 市区外单子审核
         */
        if (SystemConstant.VEHICLE_OUT_OF_CITY_WAIT_FIRST_VERIFY.equals(status) && authType.contains(SystemConstant.AUTH_TYPE_VEHICLE_OUT_OF_CITY_FIRST_VERIFY)) {
            vehicleApplicationDO.setStatus(SystemConstant.VEHICLE_VERIFY_PASS.equals(verifyRecordDO.getResult()) ?
                    SystemConstant.VEHICLE_OUT_OF_CITY_WAIT_SECOND_VERIFY : SystemConstant.VEHICLE_VERIFY_REJECT);
            return vehicleApplicationManager.update(vehicleApplicationDOList.get(0)) ? "true" : "系统异常，请稍后再试";
        }
        if (SystemConstant.VEHICLE_OUT_OF_CITY_WAIT_SECOND_VERIFY.equals(status) && authType.contains(SystemConstant.AUTH_TYPE_VEHICLE_OUT_OF_CITY_SECOND_VERIFY)) {
            vehicleApplicationDO.setStatus(SystemConstant.VEHICLE_VERIFY_PASS.equals(verifyRecordDO.getResult()) ?
                    SystemConstant.VEHICLE_VERIFY_PASS : SystemConstant.VEHICLE_VERIFY_REJECT);
            return vehicleApplicationManager.update(vehicleApplicationDOList.get(0)) ? "true" : "系统异常，请稍后再试";
        }
        return "车辆申请处于未知状态中";
    }

    @Override
    public List<VerifyRecordDO> query(VerifyRecordQuery verifyRecordQuery) {
        return verifyRecordDao.query(verifyRecordQuery);
    }

    @Override
    public int count(VerifyRecordQuery verifyRecordQuery) {
        return verifyRecordDao.count(verifyRecordQuery);
    }
}
