package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by taoxiang on 2017/5/26.
 */
@Data
public class TeamDO implements Serializable {

    private static final long serialVersionUID = 466426794505210958L;

    /**
     * 车队编号
     */
    public Integer id;

    /**
     * 车队名称
     */
    public String team;

    public String status;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
}
