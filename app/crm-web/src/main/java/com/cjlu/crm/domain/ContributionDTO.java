package com.cjlu.crm.domain;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客户贡献DTO
 *
 * @author minghui.y
 * @create 2018-04-18 20:53
 **/
@Data
@ToString
public class ContributionDTO {
    private Integer custId;

    private String custName;

    private Integer purchaseCount;

    private BigDecimal totalAccount;

    private List<SaleChanceDTO> tradeHistory;
}
