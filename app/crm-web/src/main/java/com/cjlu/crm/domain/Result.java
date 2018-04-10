package com.cjlu.crm.domain;

import java.io.Serializable;

/**
 * 响应返回结果类型
 *
 * @author minghui.y
 * @create 2018-03-24 18:16
 **/
public class Result<T> implements Serializable {
    protected int code;
    protected String desc;
    protected T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result(int code, String desc, T data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result() {
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                ", data=" + data +
                '}';
    }
}
