package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/6.
 */
@Data
public class VerifyRecordDO implements Serializable {

    private static final long serialVersionUID = -9095457292250728461L;

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 审核人Id
     */
    private Integer verifyId;

    /**
     * 审核人姓名
     */
    private String verifyName;

    /**
     * 审核事件
     */
    private String applicationEvent;

    /**
     * 审核事件Id
     */
    private int applicationId;

    /**
     * 审核结果
     */
    private String result;

    /**
     * 备注
     */
    private String remark;

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
