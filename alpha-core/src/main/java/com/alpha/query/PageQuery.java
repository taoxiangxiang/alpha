package com.alpha.query;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by taoxiang on 2017/4/2.
 */

public class PageQuery implements Serializable {

    private static final long serialVersionUID = 3451453449663158683L;

    /**
     * 页码：从1开始
     */
    private int page = 1;

    /**
     * 每页数据大小
     */
    private int pageSize = 10000;

    /**
     * 数据起始点
     */
    private int start;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return (page - 1) * pageSize;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
