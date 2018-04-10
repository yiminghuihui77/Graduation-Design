package com.cjlu.crm.service;

import com.cjlu.crm.domain.CrmChance;

import java.util.List;

/**
 * 销售机会服务
 *
 * @author minghui.y
 * @create 2018-04-07 12:26
 **/
public interface SaleChanceService {
    List<CrmChance> queryAllChance();
    void removeChance(Integer id);
}
