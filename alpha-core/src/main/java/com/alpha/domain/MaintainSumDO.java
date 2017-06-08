package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by taoxiang on 2017/5/27.
 */
@Data
public class MaintainSumDO implements Serializable {

    private static final long serialVersionUID = -7079433168049340394L;

    /**
     * 车牌号
     */
    private String vehicleNO;

    /**
     * 所属车队
     */
    private String team;

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
}
