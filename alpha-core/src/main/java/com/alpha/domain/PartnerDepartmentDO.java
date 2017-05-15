package com.alpha.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/5/9.
 */
@Data
public class PartnerDepartmentDO implements Serializable {

    private static final long serialVersionUID = -4782215217231235153L;

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

    /**
     * 地址
     */
    private String address;

    /**
     * 固定电话
     */
    private String telNumber;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 移动电话
     */
    private String mobilePhone;

    /**
     * 备注
     */
    private String remark;

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
