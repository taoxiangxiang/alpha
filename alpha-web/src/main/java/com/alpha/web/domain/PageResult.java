package com.alpha.web.domain;

/**
 * Created by taoxiang on 2017/4/15.
 */
public class PageResult<T>  extends Result<T>  {

    private int page;

    private int pageSize;

    private int number;

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
