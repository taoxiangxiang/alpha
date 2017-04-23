package com.alpha.query;

import lombok.Data;

/**
 * Created by taoxiang on 2017/4/6.
 */
@Data
public class VerifyRecordQuery extends PageQuery {

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 审核事件
     */
    private String applicationEvent;

    /**
     * 审核事件Id
     */
    private int applicationId;
}
