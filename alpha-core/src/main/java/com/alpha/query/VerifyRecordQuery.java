package com.alpha.query;

import java.util.List;
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
    private List<Integer> applicationIdList;
}
