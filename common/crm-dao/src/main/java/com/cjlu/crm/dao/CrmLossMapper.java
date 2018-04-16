package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmLoss;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmLossMapper {
    CrmLoss selectByPrimaryKey(Integer id);
    List<CrmLoss> selectAllLoss();
    int deleteLossById(@Param("id") Integer id);
}