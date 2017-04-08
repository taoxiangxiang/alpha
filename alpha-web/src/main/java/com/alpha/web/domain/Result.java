package com.alpha.web.domain;

import java.io.Serializable;

/**
 * Created by taoxiang on 2017/4/6.
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 7695704974182692802L;

    /**
     * 错误信息
     */
    private String errMsg;

    private T data;

    private boolean success = true;

    public Result() {

    }

    public Result(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        this.success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
