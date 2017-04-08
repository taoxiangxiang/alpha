package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Data
public class DepartmentDO implements Serializable {

    private static final long serialVersionUID = -1053497371001692048L;

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 所属部门
     */
    private String belongDepartmentName;

    /**
     * 部门联系人
     */
    private String departmentContact;

    /**
     * 部门联系人电话
     */
    private String departmentContactPhone;

    /**
     * 部门地址
     */
    private String departmentAddress;

    /**
     * 状态
     */
    private String status;

    /**
     * 其他属性
     */
    private String attribute;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
}
