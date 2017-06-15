package com.alpha.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by taoxiang on 2017/6/13.
 */
@Data
public class RemindDO implements Serializable {

    private static final long serialVersionUID = -1581787747652577894L;

    /**
     * 事件名称
     */
    private String eventTitle;

    /**
     * 车队
     */
    private String team;

    /**
     * 事件对象
     */
    private String eventTarget;

    /**
     * 到期指标
     */
    private String expireContent;

    /**
     * 提示信息
     */
    private String remindInfo;
}
