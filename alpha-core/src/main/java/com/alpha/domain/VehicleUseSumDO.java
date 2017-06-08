package com.alpha.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by taoxiang on 2017/5/6.
 */
@Data
public class VehicleUseSumDO {

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
     * 出车次数
     */
    private Integer number;

    /**
     * 出车里程
     */
    private Integer mile;

    /**
     * 出车时间
     */
    private Integer useTime;

    /**
     * 出车时间描述
     */
    private String useTimeDesc;

    /**
     * 总费用
     */
    private Double allFee;

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
     * 司机
     */
    private DriverDO driverDO;
}
