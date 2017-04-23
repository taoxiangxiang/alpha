package com.alpha.query;

import lombok.Data;

/**
 * Created by taoxiang on 2017/4/16.
 */
@Data
public class VehicleUseQuery extends PageQuery {

    private static final long serialVersionUID = 3699196386717420465L;

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 申请单
     */
    private Integer applicationId;

    /**
     * 司机姓名
     */
    private String driverName;

    /**
     * 车牌号
     */
    private String vehicleNO;

    /**
     * 状态
     */
    private String status;

    /**
     * 使用时间
     */
    private String startDate;

    /**
     * 使用时间
     */
    private String endDate;


}
