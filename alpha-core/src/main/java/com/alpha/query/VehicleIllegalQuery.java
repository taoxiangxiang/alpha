package com.alpha.query;

import lombok.Data;

import java.util.Date;

/**
 * Created by taoxiang on 2017/4/27.
 */
@Data
public class VehicleIllegalQuery extends PageQuery {
    private static final long serialVersionUID = 1859642747243057665L;

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 车牌号
     */
    private String vehicleNO;

    /**
     * 违章Id
     */
    private String illegalId;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    private String team;

}
