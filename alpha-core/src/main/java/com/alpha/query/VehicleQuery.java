package com.alpha.query;

import java.util.List;
import lombok.Data;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Data
public class VehicleQuery extends PageQuery {

    private static final long serialVersionUID = -4990540747023654930L;

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

    /**
     * 车辆Id
     */
    private List<Integer> idList;
}
