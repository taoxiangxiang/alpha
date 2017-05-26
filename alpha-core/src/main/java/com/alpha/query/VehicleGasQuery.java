package com.alpha.query;

import lombok.Data;

import java.util.Date;

/**
 * Created by taoxiang on 2017/4/27.
 */
@Data
public class VehicleGasQuery extends PageQuery {

    private static final long serialVersionUID = -1880629644738243890L;

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 车牌号
     */
    private String vehicleNO;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;
}
