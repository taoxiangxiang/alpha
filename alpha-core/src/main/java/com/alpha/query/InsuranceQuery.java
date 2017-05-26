package com.alpha.query;


import lombok.Data;

import java.util.Date;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Data
public class InsuranceQuery extends PageQuery {

    private static final long serialVersionUID = 6839915827089699895L;

    private Integer id;

    private String vehicleNO;

    private String startDate;

    private String endDate;
}
