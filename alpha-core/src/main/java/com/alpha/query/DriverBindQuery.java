package com.alpha.query;

import lombok.Data;

import java.util.Date;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Data
public class DriverBindQuery extends PageQuery {

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 车牌号
     */
    private String vehicleNO;

    /**
     * 司机Id
     */
    private int driverId;

    /**
     * 司机Id
     */
    private int vehicleId;

    /**
     * 姓名
     */
    private String driverName;

    /**
     * 匹配开始日期
     */
    private Date startDate;

    /**
     * 匹配结束日期
     */
    private Date endDate;

    /**
     * 状态
     */
    private String status;
}
