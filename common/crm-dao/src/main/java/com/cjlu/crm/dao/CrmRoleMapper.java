package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmRole;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmRoleMapper {
    CrmRole selectByPrimaryKey(Integer id);
}