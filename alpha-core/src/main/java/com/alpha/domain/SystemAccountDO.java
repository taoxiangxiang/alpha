package com.alpha.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alpha.constans.SystemConstant;
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
    private Date birth;

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
    private Date hireDate;

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
     * 状态
     */
    private String type;

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


    public boolean hasAuth() {
        return this.getType() != null && SystemConstant.USER_TYPE_HAS_AUTH.equals(this.getType());
    }

    public boolean hasNOAuth() {
        return this.getType() != null && SystemConstant.USER_TYPE_NO_AUTH.equals(this.getType());
    }

    public boolean isDriver() {
        return this.getType() != null && SystemConstant.USER_TYPE_DRIVER.equals(this.getType());
    }

    public int getDriverId() {
        if (attribute == null) return 0;
        try {
            JSONObject jsObject = JSON.parseObject(attribute);
            Object object = jsObject.get("driverId");
            return object == null ? 0 : Integer.valueOf(String.valueOf(object));
        } catch (Exception e) {
            return 0;
        }
    }
}
