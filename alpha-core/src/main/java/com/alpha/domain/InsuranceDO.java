package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Data
public class InsuranceDO implements Serializable {

    private static final long serialVersionUID = -7760899189703739309L;
    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 车牌号
     */
    private String vehicleNO;

    /**
     * 所属车队
     */
    private String team;

    /**
     * 投保日期
     */
    private Date insuranceStartDate;

    /**
     * 保险到期
     */
    private Date insuranceEndDate;

    /**
     * 保险种类
     */
    private String type;

    /**
     * 投保金额
     */
    private Double money;

    /**
     * 保险公司
     */
    private String companyName;

    /**
     * 经手人
     */
    private String operator;

    /**
     * 保险合同附件
     */
    private String file;

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
