package com.alpha.query;

import lombok.Data;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Data
public class VehicleQuery extends PageQuery {

    /**
     * 车牌号
     */
    private String vehicleNO;

    /**
     * 所属车队
     */
    private String team;

    /**
     * 数据库Id
     */
    private Integer id;
}
