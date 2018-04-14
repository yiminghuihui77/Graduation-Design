package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmDevelop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmDevelopMapper {
    CrmDevelop selectByPrimaryKey(Integer id);
    List<CrmDevelop> selectAllDevelops();
    int deleteDevelopById(@Param("id") Integer id);
    int insertDevelop(CrmDevelop develop);
    int updateDevelop(CrmDevelop develop);
}