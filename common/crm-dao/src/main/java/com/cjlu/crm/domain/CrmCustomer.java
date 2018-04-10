package com.cjlu.crm.domain;

import java.util.Date;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class CrmCustomer {
    /** 主键 */
    private Integer id;

    /** 客户名称 */
    private String cusName;

    /** 客户性别 */
    private String sex;

    /** 地区 */
    private String region;

    /** 客户经理编号 */
    private Integer managerId;

    /** 客户等级 */
    private String cusLevel;

    /** 客户信用度 */
    private Integer credit;

    /** 客户地址 */
    private String addr;

    /** 手机号 */
    private String phone;

    /** 备注 */
    private String memo;

    /** 状态：ENABLE、DISABLE */
    private String status;

    /** 创建时间 */
    private Date gmtCreated;

    /** 修改时间 */
    private Date gmtModified;
}