package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmLoss;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmLossMapper {
    CrmLoss selectByPrimaryKey(Integer id);
}