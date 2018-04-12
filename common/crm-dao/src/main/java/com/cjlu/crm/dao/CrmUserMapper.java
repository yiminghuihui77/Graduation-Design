package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmUser;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmUserMapper {
    CrmUser selectByPrimaryKey(Integer id);
}