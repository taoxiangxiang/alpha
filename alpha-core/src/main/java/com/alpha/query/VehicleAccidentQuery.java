package com.alpha.query;

import lombok.Data;

import java.util.Date;

/**
 * Created by taoxiang on 2017/4/27.
 */
@Data
public class VehicleAccidentQuery extends PageQuery {

    private static final long serialVersionUID = -1565848891710237130L;

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
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;
}
