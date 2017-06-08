package com.alpha.query;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by taoxiang on 2017/4/29.
 */
@Data
public class LeaveQuery extends PageQuery {
    private static final long serialVersionUID = 7494910845532002829L;

    private Date startDate;

    private Date endDate;

    private Integer id;

    private List<String> statusList;

    /**
     * 司机姓名
     */
    private String driverName;

    /**
     * 车队
     */
    private String team;

    /**
     * 司机Id
     */
    private Integer driverId;
}
