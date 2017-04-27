package com.alpha.query;

import lombok.Data;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Data
public class VehicleCheckQuery extends PageQuery {

    private static final long serialVersionUID = 2729679477865244911L;

    private Integer id;

    private String vehicleNO;

}
