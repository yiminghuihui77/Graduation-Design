package com.cjlu.crm.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 客户开发DTO
 *
 * @author minghui.y
 * @create 2018-04-14 18:50
 **/
@Data
@ToString
public class DevelopDTO {
    /** 主键 */
    private Integer id;

    /** 机会来源 */
    private String source;

    /** 客户名称 */
    private String cusName;

    /** 客户电话 */
    private String phone;

    /** 客户住址 */
    private String address;

    /** 成功几率 */
    private Integer successRate;

    /** 开发描述 */
    private String developDesc;

    /** 创建人编号(销售主管) */
    private Integer createId;
    private String createName;

    /** 指派人编号(客户经理) */
    private Integer dueId;
    private String dueName;

    /** 开发状态：0开发中1已开发 */
    private Integer status;

    /** 创建时间 */
    private Date gmtCreated;

    /** 修改时间 */
    private Date gmtModified;
}
