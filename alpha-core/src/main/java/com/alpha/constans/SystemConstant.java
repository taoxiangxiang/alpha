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
    public static final String COOKIE_NICK = "nick";

    public static final String SESSION_NICK = "nick";

    /**
     * 车辆基础信息
     */
    public static final String VEHICLE_ON_LINE = "onLine";

    public static final String VEHICLE_OFF_LINE = "offLine";

    public static final String VEHICLE_DELETE = "delete";

    /**
     * 司机基础信息
     */
    public static final String DRIVER_ON_LINE = "onLine";

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


    public static Map<String, String> applicationTypeMap = new HashMap<String, String>();

    public static Map<String, String> vehicleTypeMap = new HashMap<String, String>();

    public static Map<String, String> applicationReasonMap = new HashMap<String, String>();

    public static Map<String, String> endPlaceTypeMap = new HashMap<String, String>();

    public static Map<String, String> applicationStatus = new HashMap<String, String>();

    public static final String APPLICATION_WAIT_VERIFY = "waitVerify";
    public static final String APPLICATION_WAIT_FIRST_VERIFY = "waitVerify";
    public static final String END_PLACE_IN_CITY = "inCity";

    static {
        applicationTypeMap.put("emergency", "应急用车");
        applicationTypeMap.put("onDuty", "执法用车");

        vehicleTypeMap.put("car", "轿车");
        vehicleTypeMap.put("suv", "SUV");
        vehicleTypeMap.put("businessVehicle", "商务车");
        vehicleTypeMap.put("minibus", "面包车");
        vehicleTypeMap.put("bus", "中巴车");

        applicationReasonMap.put("1", "1");
        applicationReasonMap.put("2", "2");
        applicationReasonMap.put("3", "3");
        applicationReasonMap.put("4", "4");
        applicationReasonMap.put("5", "5");
        applicationReasonMap.put("6", "6");
        applicationReasonMap.put("7", "7");
        applicationReasonMap.put("8", "8");

        endPlaceTypeMap.put(END_PLACE_IN_CITY, "市内");
        endPlaceTypeMap.put("outOfCity", "市外");

        applicationStatus.put(APPLICATION_WAIT_VERIFY, "待审核");
        applicationStatus.put("waitFirstVerify", "待初审");
        applicationStatus.put("waitSecondVerify", "待复审");
        applicationStatus.put("passed", "审核通过");
    }

}
