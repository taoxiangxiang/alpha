package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Data
public class VehicleCheckDO implements Serializable {

    private static final long serialVersionUID = -2279670908253424114L;

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
     * 年检日期
     */
    private String checkDate;

    /**
     * 年检号
     */
    private String checkNO;

    /**
     * 年检费用
     */
    private Double money;

    /**
     * 车管所
     */
    private String checkAddress;

    /**
     * 到期时间
     */
    private String endDate;

    /**
     * 经手人
     */
    private String operator;

    /**
     * 附件
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
