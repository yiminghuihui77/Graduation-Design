package com.cjlu.crm.domain;

import lombok.Data;
import lombok.ToString;

/**
 * 支付宝用户对象
 * 个人调用API好像只能获取昵称、城市、省份，其他信息为空
 * @author minghui.y
 * @create 2018-05-23 15:28
 **/
@Data
@ToString
public class AlipayUser {

    /**真实姓名*/
    private String userName;

    /**支付宝昵称*/
    private String nickName;

    private String address;

    private String collegeName;

    private String certNo;

    private String city;

    private String degree;

    private String mobile;

    private String phone;

    private String province;
}
