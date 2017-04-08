package com.alpha.query;


import lombok.Data;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Data
public class DepartmentQuery extends PageQuery {

    private static final long serialVersionUID = -2698351111344301791L;

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 部门名称
     */
    private String departmentName;
}
