package com.alpha.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Data
public class VehicleAccidentDO {

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 车牌号
     */
    private String vehicleNO;

    /**
     * 所属车队
     */
    private String team;

    /**
     * 事故日期
     */
    private Date accidentDate;

    /**
     * 司机Id
     */
    private Integer driverId;

    /**
     * 司机
     */
    private String driverName;

    /**
     * 事故地点
     */
    private String accidentAddress;

    /**
     * 事故说明
     */
    private String accidentDesc;

    /**
     * 处理结果
     */
    private String result;

    /**
     * 处理情况
     */
    private String dealDesc;

    /**
     * 定损人
     */
    private String liablePerson;

    /**
     * 维修地点
     */
    private String maintainAddress;

    /**
     * 责任认定
     */
    private String liableDesc;

    /**
     * 保险赔偿金额
     */
    private Double money;

    /**
     * 事故附件
     */
    private String file;

    /**
     * 备注
     */
    private String remark;

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
