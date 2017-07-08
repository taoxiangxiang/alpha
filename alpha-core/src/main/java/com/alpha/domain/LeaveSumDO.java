package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by taoxiang on 2017/5/27.
 */
@Data
public class LeaveSumDO implements Serializable {

    private static final long serialVersionUID = -4186321497511635867L;

    /**
     * 司机Id
     */
    private Integer driverId;

    /**
     * 司机姓名
     */
    private String driverName;

    /**
     * 电话号码
     */
    private String mobilePhone;

    /**
     * 车队
     */
    private String team;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身份证号
     */
    private String citizenId;

    /**
     * 请假天数
     */
    private String number;

}
