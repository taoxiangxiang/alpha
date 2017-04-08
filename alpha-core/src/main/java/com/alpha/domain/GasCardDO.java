package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Data
public class GasCardDO implements Serializable {

    private static final long serialVersionUID = -1053497371001692048L;

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 油卡卡号
     */
    private String gasCardNO;

    /**
     * 油卡类型
     */
    private String gasCardType;

    /**
     * 余额
     */
    private double amount;

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
