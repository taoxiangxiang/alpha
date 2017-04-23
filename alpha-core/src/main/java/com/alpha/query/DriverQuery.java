package com.alpha.query;

import java.util.List;
import lombok.Data;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Data
public class DriverQuery extends PageQuery{

    private static final long serialVersionUID = -1822352743688452274L;

    private String name;

    private String team;

    private Integer id;

    private List<Integer> idList;
}
