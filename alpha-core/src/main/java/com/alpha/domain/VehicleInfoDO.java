package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by taoxiang on 2017/6/29.
 */
@Data
public class VehicleInfoDO implements Serializable {

    private static final long serialVersionUID = 9120323421540929163L;

    /**
     * 车牌号
     */
    private String vehicleNO;

    /**
     * 所属车队
     */
    private String team;

    /**
     * 金额
     */
    private Double gasMoney;

    /**
     * 加油量
     */
    private Double gasAmount;

    /**
     * 维修次数
     */
    private Integer weiXiuNumber;

    /**
     * 维修费用
     */
    private Double weiXiuMoney;

    /**
     * 保养次数
     */
    private Integer baoYangNumber;

    /**
     * 保养费用
     */
    private Double baoYangMoney;

    /**
     * 违章次数
     */
    private Integer fineNumber;

    /**
     * 罚款
     */
    private Double fineMoney;

    /**
     * 扣分
     */
    private Integer finePoint;
}
