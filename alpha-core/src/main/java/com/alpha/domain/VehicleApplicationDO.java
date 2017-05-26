package com.alpha.domain;

import java.util.List;

import com.alpha.constans.CalendarUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/4/6.
 */
@Data
public class VehicleApplicationDO implements Serializable {

    private static final long serialVersionUID = -7943903737325831595L;

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 申请订单号
     */
    private String applicationNO;

    /**
     * 申请单类型：应急用车、执法用车
     */
    private String applicationType;

    /**
     * 车辆类型：轿车、SUV、商务车、面包车、中巴车
     */
    private String vehicleType;

    /**
     * 紧急用车原因
     */
    private String applicationReason;

    /**
     * 用车时间
     */
    private String useDate;

    /**
     * 预计回车时间
     */
    private String predictBackDate;

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 申请人部门
     */
    private String applicationDepartment;

    /**
     * 申请人电话
     */
    private String applicantPhone;

    /**
     * 乘车人数
     */
    private int personNumber;

    /**
     * 出发地点
     */
    private String startPlace;

    /**
     * 目的地点
     */
    private String endPlace;

    /**
     * 目的地点类型：市内、市外
     */
    private String endPlaceType;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String contactsPhone;

    /**
     * 备注
     */
    private String remark;

    /**
     * 文件地址
     */
    private String file;

    /**
     * 申请单状态
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

    /**
     * 所有审核记录
     */
    private List<VerifyRecordDO> verifyRecordList;
}
