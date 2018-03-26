package com.zon.wanandroid.model;

import java.io.Serializable;

/**
 * Created by Zon on 2018/3/26 0026.
 * All Rights Resered by HangZhou @2018-2019
 */
public class ResponseData<T> implements Serializable {
    private int errorCode;
    private String errorMsg;
    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
