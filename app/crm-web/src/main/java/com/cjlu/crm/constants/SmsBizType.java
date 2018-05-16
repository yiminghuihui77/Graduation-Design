package com.cjlu.crm.constants;

/**
 * 短信业务类型
 *
 * @author minghui.y
 * @create 2018-05-16 13:30
 **/
public enum SmsBizType {
    SMS_CREATE_SERVICE(0, "创建服务短信通知"),
    SMS_PROCESS_SERVICE(1, "处理服务短信通知");

    private int code;
    private String desc;

    SmsBizType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


}
