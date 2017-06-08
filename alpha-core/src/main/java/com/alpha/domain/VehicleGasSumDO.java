package com.alpha.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by taoxiang on 2017/5/6.
 */
@Data
public class VehicleGasSumDO {

    /**
     * 车牌号
     */
    private String vehicleNO;

    /**
     * 车队
     */
    private String team;

    /**
     * 油卡编号
     */
    private String gasCardNO;

    /**
     * 金额
     */
    private Double money;

    /**
     * 加油量
     */
    private Double amount;

    /**
     * 加油里程
     */
    private Integer mile;

    /**
     * 平均油耗
     */
    private Double cost;
}
