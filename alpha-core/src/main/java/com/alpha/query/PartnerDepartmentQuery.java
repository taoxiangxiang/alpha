package com.alpha.query;


import lombok.Data;

import java.util.List;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Data
public class PartnerDepartmentQuery extends PageQuery {

    private static final long serialVersionUID = 2696223371064699932L;
    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 类型
     */
    private String type;

    private List<String> statusList;
}
