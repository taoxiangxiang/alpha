package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/26.
 */
@Data
public class VehicleIllegalDO implements Serializable{

    private static final long serialVersionUID = -8658031348791758721L;
    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 违章Id
     */
    private String illegalId;

    /**
     * 车牌号
     */
    private String vehicleNO;

    /**
     * 所属车队
     */
    private String team;

    /**
     * 违章日期
     */
    private Date illegalDate;

    /**
     * 违章原因
     */
    private String reason;

    /**
     * 罚款
     */
    private Double money;

    /**
     * 扣分
     */
    private Integer point;

    /**
     * 违章地点
     */
    private String illegalAddress;

    /**
     * 司机Id
     */
    private Integer driverId;

    /**
     * 司机
     */
    private String driverName;

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
