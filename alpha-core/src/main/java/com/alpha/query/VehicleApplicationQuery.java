package com.alpha.query;

import java.util.List;
import lombok.Data;

import java.util.Date;

/**
 * Created by taoxiang on 2017/4/6.
 */
@Data
public class VehicleApplicationQuery extends PageQuery {

    private Date startDate;

    private Date endDate;

    private Integer id;

    private List<String> statusList;

    private List<Integer> idList;

    /**
     * 申请人部门
     */
    private String applicationDepartment;

    /**
     * 申请人Id
     */
    private Integer applicantId;
}
