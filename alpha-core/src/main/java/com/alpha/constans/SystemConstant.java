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

    /**
     * 司机基础信息
     */
    public static final String DRIVER_CAN_USE = "canUse";
    public static final String DRIVER_USING = "using";
    public static final String DRIVER_OFF_LINE = "offLine";
    public static final String DRIVER_DELETE = "delete";

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
    public static final String VEHICLE_VERIFY_REJECT = "vehicleVerifyReject";

    /**
     * 维保审核状态
     */
    public static final String MAINTAIN_WAIT_FIRST_VERIFY = "maintainWaitFirstVerify";
    public static final String MAINTAIN_WAIT_SECOND_VERIFY = "maintainWaitSecondVerify";
    public static final String MAINTAIN_WAIT_THIRD_VERIFY = "maintainWaitThirdVerify";
    public static final String MAINTAIN_VERIFY_PASS = "maintainVerifyPass";
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
    public static Map<String, String> applicationStatus = new HashMap<String, String>();
    public static Map<String, String> driverStatusMap = new HashMap<String, String>();
    public static Map<String, String> vehicleStatusMap = new HashMap<String, String>();
    public static Map<String, String> gasStatusMap = new HashMap<String, String>();
    public static Map<String, String> departmentStatusMap = new HashMap<String, String>();
    public static Map<String, String> driverBindStatusMap = new HashMap<String, String>();
    public static Map<String, String> authTypeMap = new HashMap<String, String>();
    public static Map<String, String> leaveStatusMap = new HashMap<String, String>();
    public static Map<String, String> vehicleApplicationStatusMap = new HashMap<String, String>();
    public static Map<String, String> maintainStatusMap = new HashMap<String, String>();

    public static final String END_PLACE_IN_CITY = "市内";

    static {
//        applicationReasonMap.put("1", "抢险救灾以及突发事件的处置");
//        applicationReasonMap.put("2", "送、取机票文件材料及数量较多的公务用品");
//        applicationReasonMap.put("3", "参加市领导和市有关部门紧急召集的跨部门会议，参加重要接待活动，原则上只送不接，交通不便、特殊情况除外");
//        applicationReasonMap.put("4", "干部职工工作期间因伤因病需要紧急送医");
//        applicationReasonMap.put("5", "厅级离退休干部参加公务车活动和紧急就医");
//        applicationReasonMap.put("6", "经批准的其他特殊情况用车");
//        applicationReasonMap.put("7", "市级机关部门（单位）参加省里组织召开的重要会议和活动，原则上主要负责人可使用应急用车");
//        applicationReasonMap.put("8", "由市机关部门（单位）领导班子成员牵头参加省里召开的会议，凭会议通知3人以上（不含驾驶员），可使用应急用车");
//        applicationReasonMap.put("9", "上级部门领导来我市开展调研、检查、考核工作，可使用应急用车接待");
//        applicationReasonMap.put("10", "易地交流干部在双休日和法定节假日往来工作地和生活地的交通保障，在省、市相关政策出台前，暂由所在的单位负责保障");
//        applicationReasonMap.put("11", "单位统一组织的集体慰问活动，携带的物品较多的，可以使用应急用车");
//        applicationReasonMap.put("12", "单位集体组织到乡镇检查、考核公共交通不便的，可以使用应急用车");


        applicationStatus.put(VEHICLE_IN_CITY_WAIT_VERIFY, "市内出车待审核");
        applicationStatus.put(VEHICLE_OUT_OF_CITY_WAIT_FIRST_VERIFY, "市外出车待初审");
        applicationStatus.put(VEHICLE_OUT_OF_CITY_WAIT_SECOND_VERIFY, "市外出车待复审");
        applicationStatus.put("passed", "审核通过");


        driverStatusMap.put("canUse", "可调派");
        driverStatusMap.put("using","出车中");
        driverStatusMap.put("offLine","已停用");
        driverStatusMap.put("delete", "已删除");

        vehicleStatusMap.put("canUse", "可调派");
        vehicleStatusMap.put("using","出车中");
        vehicleStatusMap.put("offLine","已停用");
        vehicleStatusMap.put("delete", "已删除");

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

        leaveStatusMap.put("leaveWaitFirstVerify","待一审");
        leaveStatusMap.put("leaveWaitSecondVerify","待二审");
        leaveStatusMap.put("leaveWaitThirdVerify","待三审");
        leaveStatusMap.put("leaveVerifyPass","审批通过");
        leaveStatusMap.put("leaveVerifyReject","审批拒绝");

        vehicleApplicationStatusMap.put("inCityWaitVerify","市内待审核");
        vehicleApplicationStatusMap.put("outOfCityWaitFirstVerify","市外待一审");
        vehicleApplicationStatusMap.put("outOfCityWaitSecondVerify","市外待二审");
        vehicleApplicationStatusMap.put("vehicleVerifyPass","审批通过");
        vehicleApplicationStatusMap.put("vehicleVerifyReject","审批拒绝");

        maintainStatusMap.put("maintainWaitFirstVerify","维保待一审");
        maintainStatusMap.put("maintainWaitSecondVerify","维保待二审");
        maintainStatusMap.put("maintainWaitThirdVerify","维保待三审");
        maintainStatusMap.put("maintainVerifyPass","审批通过");
        maintainStatusMap.put("maintainVerifyReject","审批拒绝");
    }

}
