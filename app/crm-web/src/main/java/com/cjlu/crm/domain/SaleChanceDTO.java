package com.cjlu.crm.domain;

import lombok.Data;

import java.util.Date;

/**
 * 销售机会DTO
 *
 * @author minghui.y
 * @create 2018-04-07 21:52
 **/
@Data
public class SaleChanceDTO {

    /** 主键 */
    private Integer id;

    /** 客户编号 */
    private Integer custId;
    private String custName;

    /** 产品编号 */
    private Integer prodId;
    private String prodName;

    /** 购买数量 */
    private Integer amount;

    /** 机会描述 */
    private String chanceDesc;

    /** 创建时间 */
    private Date gmtCreated;

    /** 修改时间 */
    private Date gmtModified;
}
