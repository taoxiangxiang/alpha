package com.alpha.domain;

import lombok.Data;

/**
 * Created by taoxiang on 2017/5/6.
 */
@Data
public class VehicleIllegalSumDO {

    /**
     * 车牌号
     */
    private String vehicleNO;

    /**
     * 所属车队
     */
    private String team;

    /**
     * 违章次数
     */
    private Integer number;

    /**
     * 罚款
     */
    private Double money;

    /**
     * 扣分
     */
    private Integer point;
}
