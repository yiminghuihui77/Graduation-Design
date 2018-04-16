package com.cjlu.crm.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author minghui.y
 * @create 2018-04-16 17:44
 **/
@Data
@ToString
public class LossDTO {
    /** 主键 */
    private Integer id;

    /** 客户标编号 */
    private Integer custId;
    private String custName;

    /** 上次联系时间 */
    private Date lastContactTime;

    /** 确认流失时间 */
    private Date confirmLossTime;

    /** 流失状态(0暂缓流失1已流失) */
    private Integer status;

    /** 备注 */
    private String remark;
}
