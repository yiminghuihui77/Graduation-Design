package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmActivity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmActivityMapper {
    CrmActivity selectByPrimaryKey(Integer id);

    List<CrmActivity> selectByCustId(Integer id);
}