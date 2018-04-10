package com.cjlu.crm.service.impl;

import com.cjlu.crm.dao.CrmChanceMapper;
import com.cjlu.crm.domain.CrmChance;
import com.cjlu.crm.service.SaleChanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author minghui.y
 * @create 2018-04-07 12:27
 **/
@Service
public class SaleChanceServiceImpl implements SaleChanceService {

    @Autowired
    private CrmChanceMapper crmChanceMapper;

    @Override
    public List<CrmChance> queryAllChance() {
        return crmChanceMapper.selectAllChances();
    }

    @Override
    public void removeChance(Integer id) {
        crmChanceMapper.deleteById(id);
    }
}
