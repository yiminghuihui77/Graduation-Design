package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmServiceMapper {
    CrmService selectByPrimaryKey(Integer id);
    int insertService(CrmService service);
    List<CrmService> selectAllService();
    int updateRemarkById(@Param("id")Integer id, @Param("remark")String remark);
    List<CrmService> selectArchiveService();
    int archiveService(@Param("id")Integer id, @Param("starLevel")Integer starLevel);
}