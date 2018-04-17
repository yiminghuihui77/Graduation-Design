package com.cjlu.crm.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author minghui.y
 * @create 2018-04-17 15:06
 **/
@Data
@ToString
public class ServiceDTO {
    /** 主键 */
    private Integer id;

    /** 服务类型(-1建议0咨询1投诉) */
    private Integer type;

    /** 服务请求 */
    private String serviceDesc;

    /** 客户经理编号 */
    private Integer dueId;
    private String dueName;

    /** 客户编号 */
    private Integer custId;
    private String custName;

    /** 状态(0处理中1已归档) */
    private Integer status;

    /** 备注 */
    private String remark;

    /** 服务星级(1~5星) */
    private Integer starLevel;

    /** 创建时间 */
    private Date gmtCreated;

    /** 修改时间 */
    private Date gmtModified;
}
