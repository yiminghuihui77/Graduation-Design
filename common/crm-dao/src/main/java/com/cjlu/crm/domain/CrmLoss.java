package com.cjlu.crm.domain;

import java.util.Date;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
public class CrmLoss {
    /** 主键 */
    private Integer id;

    /** 客户标编号 */
    private Integer custId;

    /** 上次联系时间 */
    private Date lastContactTime;

    /** 确认流失时间 */
    private Date confirmLossTime;

    /** 流失状态(0暂缓流失1已流失) */
    private Integer status;

    /** 备注 */
    private String remark;
}