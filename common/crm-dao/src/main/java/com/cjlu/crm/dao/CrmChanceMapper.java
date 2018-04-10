package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmChance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmChanceMapper {
    CrmChance selectByPrimaryKey(Integer id);
    List<CrmChance> selectAllChances();
    void deleteById(@Param("id") Integer id);
}