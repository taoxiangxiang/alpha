package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Data
public class VehicleDO implements Serializable {

    private static final long serialVersionUID = -2922649573644306971L;

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 车牌号
     */
    private String vehicleNO;

    /**
     * 车辆品牌
     */
    private String vehicleBrand;

    /**
     * 车系
     */
    private String vehicleClass;

    /**
     * 车辆类型（轿车、SUV、商务车、面包车、中巴车等）
     */
    private String vehicleType;

    /**
     * 车辆颜色
     */
    private String colour;

    /**
     * 载重
     */
    private String load;

    /**
     * 座位
     */
    private int seat;

    /**
     * 所属车队
     */
    private String team;

    /**
     * 下一次年检日期
     */
    private Date checkDate;

    /**
     * 保险到期日期
     */
    private Date insuranceDate;

    /**
     * 下一次保养日期
     */
    private Date maintainDate;

    /**
     * 下一次保养里程
     */
    private Integer maintainMile;

    /**
     * 发动机编号
     */
    private String engineNO;

    /**
     * 车架号
     */
    private String vin;

    /**
     * 加油卡号
     */
    private String gasCardNO;

    /**
     * 加油卡类型
     */
    private String gasCardType;

    /**
     * 苏通卡号
     */
    private String suTongCardNO;

    /**
     * 驾照类型
     */
    private String licenseClass;

    /**
     * 车身图片
     */
    private String picUrl;

    /**
     * 车辆行驶里程
     */
    private Integer mile;

    /**
     * 车辆状态
     */
    private String status;

    /**
     * 申请单Id：维保单或者出车单
     */
    private Integer applicationId;

    /**
     * 其他属性
     */
    private String attribute;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
}
