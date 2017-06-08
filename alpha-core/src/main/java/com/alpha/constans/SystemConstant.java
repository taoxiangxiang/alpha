package com.alpha.constans;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taoxiang on 2017/4/3.
 */
public class SystemConstant {

    /**
     * 登录信息
     */
    public static final String COOKIE_NAME = "name";
    public static final String SESSION_NAME = "name";

    /**
     * 车辆基础信息
     */
    public static final String VEHICLE_CAN_USE = "canUse";
    public static final String VEHICLE_USING = "using";
    public static final String VEHICLE_OFF_LINE = "offLine";
    public static final String VEHICLE_DELETE = "delete";
    public static final String VEHICLE_MAINTAIN = "maintain";

    /**
     * 司机基础信息
     */
    public static final String DRIVER_CAN_USE = "canUse";
    public static final String DRIVER_USING = "using";
    public static final String DRIVER_OFF_LINE = "offLine";
    public static final String DRIVER_DELETE = "delete";
    public static final String DRIVER_LEAVING = "leaving";

    /**
     * 司机--车辆信息
     */
    public static final String DRIVER_BIND_VEHICLE = "bind";
    public static final String DRIVER_UNBIND_VEHICLE = "unbind";

    /**
     * 系统用户基础信息
     */
    public static final String ACCOUNT_ON_LINE = "onLine";
    public static final String ACCOUNT_OFF_LINE = "offLine";
    public static final String ACCOUNT_DELETE = "delete";

    public static final String USER_TYPE_DRIVER = "driver";
    public static final String USER_TYPE_HAS_AUTH = "hasAuth";
    public static final String USER_TYPE_NO_AUTH = "noAuth";

    /**
     * 部门信息
     */
    public static final String DEPARTMENT_ON_LINE = "onLine";
    public static final String DEPARTMENT_DELETE = "delete";

    /**
     * 车队信息
     */
    public static final String TEAM_ON_LINE = "onLine";
    public static final String TEAM_DELETE = "delete";

    /**
     * 油卡信息
     */
    public static final String GAS_CARD_ON_LINE = "onLine";
    public static final String GAS_CARD_DELETE = "delete";

    /**
     * 车辆使用记录信息
     */
    public static final String VEHICLE_USE_RECORD_EFFECTIVE = "effective";
    public static final String VEHICLE_USE_RECORD_INVALID = "invalid";

    /**
     * 审核事件
     */
    public static final String VERIFY_EVENT_VEHICLE_APPLICATION = "verifyEventVehicleApplication";
    public static final String VERIFY_EVENT_MAINTAIN_APPLICATION = "verifyEventMaintainApplication";
    public static final String VERIFY_EVENT_LEAVE_APPLICATION = "verifyEventLeaveApplication";

    /**
     * 审核权限
     */
    public static final String AUTH_TYPE_VEHICLE_IN_CITY_VERIFY = "inCityVerifyAuth";
    public static final String AUTH_TYPE_VEHICLE_OUT_OF_CITY_FIRST_VERIFY = "outOfCityFirstVerifyAuth";
    public static final String AUTH_TYPE_VEHICLE_OUT_OF_CITY_SECOND_VERIFY = "outOfCitySecondVerifyAuth";

    public static final String AUTH_TYPE_MAINTAIN_FIRST_VERIFY = "maintainFirstVerifyAuth";
    public static final String AUTH_TYPE_MAINTAIN_SECOND_VERIFY = "maintainSecondVerifyAuth";
    public static final String AUTH_TYPE_MAINTAIN_THIRD_VERIFY = "maintainThirdVerifyAuth";

    public static final String AUTH_TYPE_LEAVE_FIRST_VERIFY = "leaveFirstVerifyAuth";
    public static final String AUTH_TYPE_LEAVE_SECOND_VERIFY = "leaveSecondVerifyAuth";
    public static final String AUTH_TYPE_LEAVE_THIRD_VERIFY = "leaveThirdVerifyAuth";

    public static final String AUTH_TYPE_VEHICLE_USE_SCHEDULE = "vehicleUseScheduleAuth";
    public static final String AUTH_TYPE_MAINTAIN_SCHEDULE = "maintainScheduleAuth";
    public static final String AUTH_TYPE_SEE_ALL = "seeAllAuth";

    /**
     * 审核结果
     */
    public static final String VERIFY_PASS = "同意";
    public static final String VERIFY_REJECT = "不同意";

    /**
     * 车辆审核状态
     */
    public static final String VEHICLE_IN_CITY_WAIT_VERIFY = "inCityWaitVerify";
    public static final String VEHICLE_OUT_OF_CITY_WAIT_FIRST_VERIFY = "outOfCityWaitFirstVerify";
    public static final String VEHICLE_OUT_OF_CITY_WAIT_SECOND_VERIFY = "outOfCityWaitSecondVerify";
    public static final String VEHICLE_VERIFY_PASS = "vehicleVerifyPass";
    public static final String VEHICLE_ALREADY_SCHEDULE = "vehicleAlreadySchedule";
    public static final String VEHICLE_VERIFY_REJECT = "vehicleVerifyReject";

    /**
     * 维保审核状态
     */
    public static final String MAINTAIN_WAIT_FIRST_VERIFY = "maintainWaitFirstVerify";
    public static final String MAINTAIN_WAIT_SECOND_VERIFY = "maintainWaitSecondVerify";
    public static final String MAINTAIN_WAIT_THIRD_VERIFY = "maintainWaitThirdVerify";
    public static final String MAINTAIN_VERIFY_PASS = "maintainVerifyPass";
    public static final String MAINTAIN_ALREADY_SCHEDULE = "maintainAlreadySchedule";
    public static final String MAINTAIN_ALREADY_PICK_UP = "maintainAlreadyPickUp";
    public static final String MAINTAIN_VERIFY_REJECT = "maintainVerifyReject";

    /**
     * 请假审核状态
     */
    public static final String LEAVE_WAIT_FIRST_VERIFY = "leaveWaitFirstVerify";
    public static final String LEAVE_WAIT_SECOND_VERIFY = "leaveWaitSecondVerify";
    public static final String LEAVE_WAIT_THIRD_VERIFY = "leaveWaitThirdVerify";
    public static final String LEAVE_VERIFY_PASS = "leaveVerifyPass";
    public static final String LEAVE_VERIFY_REJECT = "leaveVerifyReject";

    public static Map<String, String> applicationTypeMap = new HashMap<String, String>();
    public static Map<String, String> applicationReasonMap = new HashMap<String, String>();
    public static Map<String, String> endPlaceTypeMap = new HashMap<String, String>();
    public static Map<String, String> driverStatusMap = new HashMap<String, String>();
    public static Map<String, String> vehicleStatusMap = new HashMap<String, String>();
    public static Map<String, String> gasStatusMap = new HashMap<String, String>();
    public static Map<String, String> departmentStatusMap = new HashMap<String, String>();
    public static Map<String, String> driverBindStatusMap = new HashMap<String, String>();
    public static Map<String, String> authTypeMap = new HashMap<String, String>();
    public static Map<String, String> leaveStatusMap = new HashMap<String, String>();
    public static Map<String, String> vehicleApplicationStatusMap = new HashMap<String, String>();
    public static Map<String, String> maintainStatusMap = new HashMap<String, String>();
    public static Map<String, String> verifyResultMap = new HashMap<String, String>();

    public static final String END_PLACE_IN_CITY = "市内";
    private static final String END_PLACE_OUT_OF_CITY = "市内";

    static {

        endPlaceTypeMap.put(END_PLACE_IN_CITY, "市内");
        endPlaceTypeMap.put(END_PLACE_OUT_OF_CITY, "市外");

        verifyResultMap.put(VERIFY_PASS, "同意");
        verifyResultMap.put(VERIFY_REJECT, "不同意");

        driverStatusMap.put("canUse", "可调派");
        driverStatusMap.put("using", "出车中");
        driverStatusMap.put("offLine", "已停用");
        driverStatusMap.put("delete", "已删除");
        driverStatusMap.put("leaving", "休假中");

        vehicleStatusMap.put("canUse", "可调派");
        vehicleStatusMap.put("using", "出车中");
        vehicleStatusMap.put("offLine", "已停用");
        vehicleStatusMap.put("delete", "已删除");
        vehicleStatusMap.put("maintain", "维保中");

        gasStatusMap.put("onLine", "有效");
        gasStatusMap.put("delete", "已删除");

        departmentStatusMap.put("onLine", "有效");
        departmentStatusMap.put("delete", "已删除");

        driverBindStatusMap.put("bind", "绑定");
        driverBindStatusMap.put("unbind", "已解绑");

        authTypeMap.put("inCityVerifyAuth", "市内用车审核");
        authTypeMap.put("outOfCityFirstVerifyAuth", "市外用车一审");
        authTypeMap.put("outOfCitySecondVerifyAuth", "市外用车二审");
        authTypeMap.put("maintainFirstVerifyAuth", "维保一审");
        authTypeMap.put("maintainSecondVerifyAuth", "维保二审");
        authTypeMap.put("maintainThirdVerifyAuth", "维保三审");
        authTypeMap.put("leaveFirstVerifyAuth", "病事假一审");
        authTypeMap.put("leaveSecondVerifyAuth", "病事假二审");
        authTypeMap.put("leaveThirdVerifyAuth", "病事假三审");
        authTypeMap.put(AUTH_TYPE_VEHICLE_USE_SCHEDULE, "车辆调度");
        authTypeMap.put(AUTH_TYPE_MAINTAIN_SCHEDULE, "维保调派");
        authTypeMap.put(AUTH_TYPE_SEE_ALL, "查看所有功能");

        leaveStatusMap.put("leaveWaitFirstVerify", "待一审");
        leaveStatusMap.put("leaveWaitSecondVerify", "待二审");
        leaveStatusMap.put("leaveWaitThirdVerify", "待三审");
        leaveStatusMap.put("leaveVerifyPass", "审批通过");
        leaveStatusMap.put("leaveVerifyReject", "审批拒绝");

        vehicleApplicationStatusMap.put(VEHICLE_IN_CITY_WAIT_VERIFY, "市内待审核");
        vehicleApplicationStatusMap.put(VEHICLE_OUT_OF_CITY_WAIT_FIRST_VERIFY, "市外待一审");
        vehicleApplicationStatusMap.put(VEHICLE_OUT_OF_CITY_WAIT_SECOND_VERIFY, "市外待二审");
        vehicleApplicationStatusMap.put(VEHICLE_VERIFY_PASS, "审批通过");
        vehicleApplicationStatusMap.put(VEHICLE_VERIFY_REJECT, "审批拒绝");
        vehicleApplicationStatusMap.put(VEHICLE_ALREADY_SCHEDULE, "已调派");

        maintainStatusMap.put(MAINTAIN_WAIT_FIRST_VERIFY, "维保待一审");
        maintainStatusMap.put(MAINTAIN_WAIT_SECOND_VERIFY, "维保待二审");
        maintainStatusMap.put(MAINTAIN_WAIT_THIRD_VERIFY, "维保待三审");
        maintainStatusMap.put(MAINTAIN_VERIFY_PASS, "审批通过");
        maintainStatusMap.put(MAINTAIN_VERIFY_REJECT, "审批拒绝");
        maintainStatusMap.put(MAINTAIN_ALREADY_SCHEDULE, "已派出");
        maintainStatusMap.put(MAINTAIN_ALREADY_PICK_UP, "已取车");
    }

}
