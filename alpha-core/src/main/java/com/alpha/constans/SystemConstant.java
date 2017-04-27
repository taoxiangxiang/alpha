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

    /**
     * 审核权限
     */
    public static final String AUTH_TYPE_VEHICLE_IN_CITY_VERIFY = "inCityVerifyAuth";
    public static final String AUTH_TYPE_VEHICLE_OUT_OF_CITY_FIRST_VERIFY = "outOfCityFirstVerifyAuth";
    public static final String AUTH_TYPE_VEHICLE_OUT_OF_CITY_SECOND_VERIFY = "outOfCitySecondVerifyAuth";

    public static final String AUTH_TYPE_MAINTAIN_FIRST_VERIFY = "maintainFirstVerifyAuth";
    public static final String AUTH_TYPE_MAINTAIN_SECOND_VERIFY = "maintainSecondVerifyAuth";
    public static final String AUTH_TYPE_MAINTAIN_THIRD_VERIFY = "maintainThirdVerifyAuth";

    /**
     * 审核结果
     */
    public static final String VERIFY_PASS = "pass";
    public static final String VERIFY_REJECT = "reject";

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

    public static Map<String, String> applicationTypeMap = new HashMap<String, String>();

//    public static Map<String, String> vehicleTypeMap = new HashMap<String, String>();

    public static Map<String, String> applicationReasonMap = new HashMap<String, String>();

    public static Map<String, String> endPlaceTypeMap = new HashMap<String, String>();

    public static Map<String, String> applicationStatus = new HashMap<String, String>();

    public static final String END_PLACE_IN_CITY = "inCity";

    static {
        applicationTypeMap.put("emergency", "应急用车");
        applicationTypeMap.put("onDuty", "执法用车");

//        vehicleTypeMap.put("car", "轿车");
//        vehicleTypeMap.put("suv", "SUV");
//        vehicleTypeMap.put("businessVehicle", "商务车");
//        vehicleTypeMap.put("minibus", "面包车");
//        vehicleTypeMap.put("bus", "中巴车");

        applicationReasonMap.put("1", "抢险救灾以及突发事件的处置");
        applicationReasonMap.put("2", "送、取机票文件材料及数量较多的公务用品");
        applicationReasonMap.put("3", "参加市领导和市有关部门紧急召集的跨部门会议，参加重要接待活动，原则上只送不接，交通不便、特殊情况除外");
        applicationReasonMap.put("4", "干部职工工作期间因伤因病需要紧急送医");
        applicationReasonMap.put("5", "厅级离退休干部参加公务车活动和紧急就医");
        applicationReasonMap.put("6", "经批准的其他特殊情况用车");
        applicationReasonMap.put("7", "市级机关部门（单位）参加省里组织召开的重要会议和活动，原则上主要负责人可使用应急用车");
        applicationReasonMap.put("8", "由市机关部门（单位）领导班子成员牵头参加省里召开的会议，凭会议通知3人以上（不含驾驶员），可使用应急用车");
        applicationReasonMap.put("9", "上级部门领导来我市开展调研、检查、考核工作，可使用应急用车接待");
        applicationReasonMap.put("10", "易地交流干部在双休日和法定节假日往来工作地和生活地的交通保障，在省、市相关政策出台前，暂由所在的单位负责保障");
        applicationReasonMap.put("11", "单位统一组织的集体慰问活动，携带的物品较多的，可以使用应急用车");
        applicationReasonMap.put("12", "单位集体组织到乡镇检查、考核公共交通不便的，可以使用应急用车");


        endPlaceTypeMap.put(END_PLACE_IN_CITY, "市内");
        endPlaceTypeMap.put("outOfCity", "市外");

        applicationStatus.put(VEHICLE_IN_CITY_WAIT_VERIFY, "市内出车待审核");
        applicationStatus.put(VEHICLE_OUT_OF_CITY_WAIT_FIRST_VERIFY, "市外出车待初审");
        applicationStatus.put(VEHICLE_OUT_OF_CITY_WAIT_SECOND_VERIFY, "市外出车待复审");
        applicationStatus.put("passed", "审核通过");
    }

}
