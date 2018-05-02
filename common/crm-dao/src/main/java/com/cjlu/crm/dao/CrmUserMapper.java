package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmUserMapper {
    CrmUser selectByPrimaryKey(Integer id);

    List<CrmUser> selectAllManagers();

    List<CrmUser> selectAllSaleCharges();

    CrmUser selectByLogin(@Param("account") String account, @Param("password") String password);
}