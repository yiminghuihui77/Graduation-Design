package com.cjlu.crm.domain;

import java.util.Date;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class CrmChance {
    /** 主键 */
    private Integer id;

    /** 客户编号 */
    private Integer custId;

    /** 产品编号 */
    private Integer prodId;

    /** 购买数量 */
    private Integer amount;

    /** 机会描述 */
    private String chanceDesc;

    /** 创建时间 */
    private Date gmtCreated;

    /** 修改时间 */
    private Date gmtModified;
}