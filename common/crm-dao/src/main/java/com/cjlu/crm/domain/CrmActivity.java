package com.cjlu.crm.domain;

import java.util.Date;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class CrmActivity {
    /** 主键 */
    private Integer id;

    /** 客户编号 */
    private Integer cusId;

    /** 交往日期 */
    private Date date;

    /** 交往地点 */
    private String place;

    /** 详细描述 */
    private String activityDesc;

    /** 创建时间 */
    private Date gmtCreated;

    /** 修改时间 */
    private Date gmtModified;
}