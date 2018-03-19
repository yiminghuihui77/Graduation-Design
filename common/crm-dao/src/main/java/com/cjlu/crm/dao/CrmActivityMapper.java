package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmActivity;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmActivityMapper {
    CrmActivity selectByPrimaryKey(Integer id);
}