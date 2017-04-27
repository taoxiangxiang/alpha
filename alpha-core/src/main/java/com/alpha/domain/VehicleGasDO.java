package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Data
public class VehicleGasDO implements Serializable{

    private static final long serialVersionUID = -1345776145497393283L;

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
     * 加油日期
     */
    private String gasDate;

    /**
     * 油气站
     */
    private String gasAddress;

    /**
     * 付款方式
     */
    private String payType;

    /**
     * 油料标号
     */
    private String gasType;

    /**
     * 油卡编号
     */
    private String gasCardNO;

    /**
     * 单价
     */
    private Double price;

    /**
     * 金额
     */
    private Double money;

    /**
     * 加油量
     */
    private Double amount;

    /**
     * 本次加油里程
     */
    private Integer curMile;

    /**
     * 上次加油里程
     */
    private Integer beforeMile;

    /**
     * 经手人
     */
    private String operator;

    /**
     * 加油单附件
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
