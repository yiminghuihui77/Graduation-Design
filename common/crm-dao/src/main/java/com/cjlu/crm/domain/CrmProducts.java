package com.cjlu.crm.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class CrmProducts {
    /** 主键 */
    private Integer id;

    /** 产品名称 */
    private String prodName;

    /** 产品描述 */
    private String prodDesc;

    /** 单位 */
    private String prodUnit;

    /** 进价(单位：万元) */
    private BigDecimal purchasePrice;

    /** 售价(单位：万元) */
    private BigDecimal salePrice;

    /** 库存 */
    private Integer prodStore;

    /** 创建时间 */
    private Date gmtCreated;

    /** 修改时间 */
    private Date gmtModified;
}