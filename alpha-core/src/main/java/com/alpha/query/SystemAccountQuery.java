package com.alpha.query;

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
    private Long id;

    /**
     * 姓名
     */
    private String name;
}
