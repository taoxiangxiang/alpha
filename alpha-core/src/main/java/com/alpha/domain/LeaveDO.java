package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/29.
 */
@Data
public class LeaveDO implements Serializable {

    private static final long serialVersionUID = 8489545849309449376L;

    /**
     * 数据库id
     */
    private Integer id;

    /**
     * 司机Id
     */
    private Integer driverId;

    /**
     * 司机姓名
     */
    private String driverName;

    /**
     * 电话号码
     */
    private String mobilePhone;

    /**
     * 车队
     */
    private String team;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 原因
     */
    private String reason;

    /**
     * 备注
     */
    private String remark;

    /**
     * 文件
     */
    private String file;

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
