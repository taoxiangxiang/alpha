package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/3/20.
 */
@Data
public class SystemAccountDO implements Serializable {

    private static final long serialVersionUID = 5300742767621803829L;

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 密码
     */
    private String password;

    /**
     * 身份证号码
     */
    private String citizenId;

    /**
     * 生日
     */
    private String birth;

    /**
     * 名族
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
     * 固定电话
     */
    private String telNumber;

    /**
     * 手机号码
     */
    private String mobilePhone;

    /**
     * 邮箱地址
     */
    private String mailbox;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 入职日期
     */
    private String hireDate;

    /**
     * 职位
     */
    private String position;

    /**
     * 部门
     */
    private String department;

    /**
     * 拥有权限
     */
    private String authType;

    /**
     * 照片地址
     */
    private String picUrl;

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
