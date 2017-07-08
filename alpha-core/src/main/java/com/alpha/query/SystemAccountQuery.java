package com.alpha.query;

import java.util.List;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by taoxiang on 2017/3/20.
 */
@Data
public class SystemAccountQuery extends PageQuery {

    private static final long serialVersionUID = -8708649458304478739L;

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 姓名 模糊查询
     */
    private String likeName;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 身份证号码
     */
    private String citizenId;

    /**
     * 人员类型
     */
    private List<String> typeList;

    /**
     * 状态类型
     */
    private List<String> statusList;
}
