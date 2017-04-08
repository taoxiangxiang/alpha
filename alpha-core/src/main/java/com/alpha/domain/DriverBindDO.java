package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Data
public class DriverBindDO implements Serializable {
    private static final long serialVersionUID = -5575769105120500562L;

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 车辆Id
     */
    private Integer vehicleId;

    /**
     * 车牌号
     */
    private String vehicleNO;

    /**
     * 司机Id
     */
    private Integer driverId;

    /**
     * 姓名
     */
    private String driverName;

    /**
     * 车队
     */
    private String team;

    /**
     * 绑定日期
     */
    private Date bindDate;

    /**
     * 解绑定日期
     */
    private Date unbindDate;

    /**
     * 状态
     */
    private String status;

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
