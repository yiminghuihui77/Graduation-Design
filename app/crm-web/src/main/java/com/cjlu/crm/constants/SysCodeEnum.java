package com.cjlu.crm.constants;

/**
 * 系统编码枚举
 *
 * @author minghui.y
 * @create 2018-03-26 20:18
 **/
public enum SysCode {
    OK(0, "请求成功"),
    ERR_SYS(-1, "系统繁忙"),
    ERR_PARAM(-2, "参数错误");


    private final int value;
    private final String info;

    SysCode(int value, String info) {
        this.value = value;
        this.info = info;
    }

    public int getValue() {
        return value;
    }

    public String getInfo() {
        return info;
    }
}
