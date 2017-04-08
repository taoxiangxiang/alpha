package com.alpha.query;

import lombok.Data;

/**
 * Created by taoxiang on 2017/4/8.
 */
@Data
public class GasCardQuery extends PageQuery{

    /**
     * 数据库Id
     */
    private Integer id;

    /**
     * 油卡卡号
     */
    private String gasCardNO;

}
