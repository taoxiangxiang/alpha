package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Data
public class DriverDO implements Serializable {

    private static final long serialVersionUID = -2226352946134723911L;

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

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
     * 生日
     */
    private Date birth;

    /**
     * 民族
     */
    private String ethnicGroup;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 学历
     */
    private String education;

    /**
     * 电话号码
     */
    private String mobilePhone;

    /**
     * 邮箱
     */
    private String mailbox;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 驾驶证号
     */
    private String drivingLicense;

    /**
     * 驾驶证类型
     */
    private String licenseClass;

    /**
     * 驾照开始日期
     */
    private Date licenseStart;

    /**
     * 驾照结束日期
     */
    private Date licenseEnd;

    /**
     * 发证机关
     */
    private String offerLicense;

    /**
     * 备注
     */
    private String remark;

    /**
     * 个人照片
     */
    private String personUrl;

    /**
     * 驾照照片
     */
    private String licenseUrl;

    /**
     * 人员状态
     */
    private String status;

    /**
     * 申请单Id：维保单或者出车单或者病假单
     */
    private Integer applicationId;

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
