package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/16.
 */
@Data
public class VehicleUseDO implements Serializable {

    private static final long serialVersionUID = -7733349258111985899L;

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 申请单
     */
    private Integer applicationId;

    /**
     * 申请人电话
     */
    private String applicantPhone;

    /**
     * 申请单编号
     */
    private String applicationNO;

    /**
     * 司机Id
     */
    private Integer driverId;

    /**
     * 司机姓名
     */
    private String driverName;

    /**
     * 车队
     */
    private String team;

    /**
     * 司机电话
     */
    private String driverPhone;

    /**
     * 车辆Id
     */
    private Integer vehicleId;

    /**
     * 车牌号
     */
    private String vehicleNO;

    /**
     * 状态
     */
    private String status;

    /**
     * 实际开始时间
     */
    private Date actualStartDate;

    /**
     * 实际结束时间
     */
    private Date actualEndDate;

    /**
     * 实际回车时间
     */
    private Date actualBackDate;

    /**
     * 使用时间
     */
    private Integer useTime;

    /**
     * 出车里程
     */
    private Integer startMile;

    /**
     * 结束里程
     */
    private Integer endMile;

    /**
     * 服务费用
     */
    private Double fuwuFee;

    /**
     * 差旅费
     */
    private Double cailvFee;

    /**
     * 加班费
     */
    private Double jiabanFee;

    /**
     * 过路费用
     */
    private Double guoluFee;

    /**
     * 过桥费用
     */
    private Double guoqiaoFee;

    /**
     * 洗车费用
     */
    private Double xicheFee;

    /**
     * 停车费用
     */
    private Double tingcheFee;

    /**
     * 其他费用
     */
    private Double otherFee;

    /**
     * 备注
     */
    private String remark;

    /**
     * 其他属性
     */
    private String attribute;

    /**
     * 评价
     */
    private Double point;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 申请单
     */
    private VehicleApplicationDO vehicleApplicationDO;

    /**
     * 司机
     */
    private DriverDO driverDO;

    /**
     * 是否已经登记
     */
    private boolean alreadyCheck;

}
