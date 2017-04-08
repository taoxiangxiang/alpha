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

    private Long id;

    private List<String> statusList;
}
