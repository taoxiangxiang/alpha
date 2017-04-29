package com.alpha.manager.impl;

import com.alibaba.citrus.util.CollectionUtil;
import com.alibaba.citrus.util.StringUtil;
import com.alpha.constans.SystemConstant;
import com.alpha.dao.VerifyRecordDao;
import com.alpha.domain.*;
import com.alpha.manager.*;
import com.alpha.query.*;
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
    private MaintainManager maintainManager;
    @Resource
    private LeaveManager leaveManager;
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
        /**
         * 维保申请审核
         */
        if (SystemConstant.VERIFY_EVENT_MAINTAIN_APPLICATION.equals(verifyRecordDO.getApplicationEvent())) {
            result = verifyMaintainApplication(authType, verifyRecordDO);
        }

        /**
         * 请假申请审核
         */
        if (SystemConstant.VERIFY_EVENT_LEAVE_APPLICATION.equals(verifyRecordDO.getApplicationEvent())) {
            result = verifyLeaveApplication(authType, verifyRecordDO);
        }
        return "true".equals(result) && verifyRecordDao.insert(verifyRecordDO) ? "true" : result;
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
            vehicleApplicationDO.setStatus(SystemConstant.VERIFY_PASS.equals(verifyRecordDO.getResult()) ?
                    SystemConstant.VEHICLE_VERIFY_PASS : SystemConstant.VEHICLE_VERIFY_REJECT);
            return vehicleApplicationManager.update(vehicleApplicationDO) ? "true" : "系统异常，请稍后再试";
        }
        /**
         * 市区外单子审核
         */
        if (SystemConstant.VEHICLE_OUT_OF_CITY_WAIT_FIRST_VERIFY.equals(status) && authType.contains(SystemConstant.AUTH_TYPE_VEHICLE_OUT_OF_CITY_FIRST_VERIFY)) {
            vehicleApplicationDO.setStatus(SystemConstant.VERIFY_PASS.equals(verifyRecordDO.getResult()) ?
                    SystemConstant.VEHICLE_OUT_OF_CITY_WAIT_SECOND_VERIFY : SystemConstant.VEHICLE_VERIFY_REJECT);
            return vehicleApplicationManager.update(vehicleApplicationDO) ? "true" : "系统异常，请稍后再试";
        }
        if (SystemConstant.VEHICLE_OUT_OF_CITY_WAIT_SECOND_VERIFY.equals(status) && authType.contains(SystemConstant.AUTH_TYPE_VEHICLE_OUT_OF_CITY_SECOND_VERIFY)) {
            vehicleApplicationDO.setStatus(SystemConstant.VERIFY_PASS.equals(verifyRecordDO.getResult()) ?
                    SystemConstant.VEHICLE_VERIFY_PASS : SystemConstant.VEHICLE_VERIFY_REJECT);
            return vehicleApplicationManager.update(vehicleApplicationDO) ? "true" : "系统异常，请稍后再试";
        }
        return "车辆申请处于未知状态中";
    }

    private String verifyMaintainApplication(String authType, VerifyRecordDO verifyRecordDO) {
        MaintainQuery maintainQuery = new MaintainQuery();
        maintainQuery.setId(verifyRecordDO.getApplicationId());
        List<MaintainDO> maintainDOList = maintainManager.query(maintainQuery);
        if (maintainDOList == null || maintainDOList.size() == 0) {
            return "系统中不存在该维保申请单";
        }
        MaintainDO maintainDO = maintainDOList.get(0);
        String status = maintainDO.getStatus();
        /**
         * 第一次审核
         */
        if (SystemConstant.MAINTAIN_WAIT_FIRST_VERIFY.equals(status) && authType.contains(SystemConstant.AUTH_TYPE_MAINTAIN_FIRST_VERIFY)) {
            maintainDO.setStatus(SystemConstant.VERIFY_REJECT.equals(verifyRecordDO.getResult()) ?
                    SystemConstant.MAINTAIN_VERIFY_REJECT : SystemConstant.MAINTAIN_WAIT_SECOND_VERIFY);
            return maintainManager.update(maintainDO) ? "true" : "系统异常，请稍后再试";
        }
        /**
         * 第二次审核
         */
        if (SystemConstant.MAINTAIN_WAIT_SECOND_VERIFY.equals(status) && authType.contains(SystemConstant.AUTH_TYPE_MAINTAIN_SECOND_VERIFY)) {
            maintainDO.setStatus(SystemConstant.VERIFY_REJECT.equals(verifyRecordDO.getResult()) ?
                    SystemConstant.MAINTAIN_VERIFY_REJECT : SystemConstant.MAINTAIN_WAIT_THIRD_VERIFY);
            return maintainManager.update(maintainDO) ? "true" : "系统异常，请稍后再试";
        }
        /**
         * 第三次审核
         */
        if (SystemConstant.MAINTAIN_WAIT_THIRD_VERIFY.equals(status) && authType.contains(SystemConstant.AUTH_TYPE_MAINTAIN_THIRD_VERIFY)) {
            maintainDO.setStatus(SystemConstant.VERIFY_REJECT.equals(verifyRecordDO.getResult()) ?
                    SystemConstant.MAINTAIN_VERIFY_REJECT : SystemConstant.MAINTAIN_VERIFY_PASS);
            return maintainManager.update(maintainDO) ? "true" : "系统异常，请稍后再试";
        }
        return "维保申请处于未知状态中";
    }

    private String verifyLeaveApplication(String authType, VerifyRecordDO verifyRecordDO) {
        LeaveQuery leaveQuery = new LeaveQuery();
        leaveQuery.setId(verifyRecordDO.getApplicationId());
        List<LeaveDO> leaveDOList = leaveManager.query(leaveQuery);
        if (leaveDOList == null || leaveDOList.size() == 0) {
            return "系统中不存在该请假申请单";
        }
        LeaveDO leaveDO = leaveDOList.get(0);
        String status = leaveDO.getStatus();
        /**
         * 第一次审核
         */
        if (SystemConstant.LEAVE_WAIT_FIRST_VERIFY.equals(status) && authType.contains(SystemConstant.AUTH_TYPE_LEAVE_FIRST_VERIFY)) {
            leaveDO.setStatus(SystemConstant.VERIFY_REJECT.equals(verifyRecordDO.getResult()) ?
                    SystemConstant.LEAVE_VERIFY_REJECT : SystemConstant.LEAVE_WAIT_SECOND_VERIFY);
            return leaveManager.update(leaveDO) ? "true" : "系统异常，请稍后再试";
        }
        /**
         * 第二次审核
         */
        if (SystemConstant.LEAVE_WAIT_SECOND_VERIFY.equals(status) && authType.contains(SystemConstant.AUTH_TYPE_LEAVE_SECOND_VERIFY)) {
            leaveDO.setStatus(SystemConstant.VERIFY_REJECT.equals(verifyRecordDO.getResult()) ?
                    SystemConstant.LEAVE_VERIFY_REJECT : SystemConstant.LEAVE_WAIT_THIRD_VERIFY);
            return leaveManager.update(leaveDO) ? "true" : "系统异常，请稍后再试";
        }
        /**
         * 第三次审核
         */
        if (SystemConstant.LEAVE_WAIT_THIRD_VERIFY.equals(status) && authType.contains(SystemConstant.AUTH_TYPE_LEAVE_THIRD_VERIFY)) {
            leaveDO.setStatus(SystemConstant.VERIFY_REJECT.equals(verifyRecordDO.getResult()) ?
                    SystemConstant.LEAVE_VERIFY_REJECT : SystemConstant.LEAVE_VERIFY_PASS);
            return leaveManager.update(leaveDO) ? "true" : "系统异常，请稍后再试";
        }
        return "请假申请处于未知状态中";
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
