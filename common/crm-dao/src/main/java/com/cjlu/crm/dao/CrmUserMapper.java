package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmUserMapper {
    CrmUser selectByPrimaryKey(Integer id);

    List<CrmUser> selectAllManagers();

    List<CrmUser> selectAllSaleCharges();
}