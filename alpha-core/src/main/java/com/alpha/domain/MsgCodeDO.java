package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/6/11.
 */
@Data
public class MsgCodeDO implements Serializable {

    private static final long serialVersionUID = 1469860557168668443L;

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 验证码
     */
    private String code;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
}
