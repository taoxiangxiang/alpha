package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Data
public class MaintainDO implements Serializable {

    private static final long serialVersionUID = -8967746144836211837L;

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
     * 申请人
     */
    private String applicant;

    /**
     * 申请人电话
     */
    private String applicantPhone;

    /**
     * 预计取车时间
     */
    private String predictPickUpDate;

    /**
     * 实际取车时间
     */
    private String actualPickUpDate;

    /**
     * 维保类别
     */
    private String type;

    /**
     * 维保内容
     */
    private String maintainContent;

    /**
     * 维保厂地址
     */
    private String maintainAddress;

    /**
     * 维保时间
     */
    private String maintainDate;

    /**
     * 车辆里程
     */
    private Integer mile;

    /**
     * 申请原因
     */
    private String reason;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String applicationRemark;

    /**
     * 备注
     */
    private String pickUpRemark;

    /**
     * 文件地址
     */
    private String file;

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
