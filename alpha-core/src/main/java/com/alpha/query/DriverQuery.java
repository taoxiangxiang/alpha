package com.alpha.query;

import lombok.Data;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Data
public class DriverQuery extends PageQuery{

    private String name;

    private String team;

    private Integer id;
}
