package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmCustomer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmCustomerMapper {
    CrmCustomer selectByPrimaryKey(Integer id);

    List<CrmCustomer> selectAllCustomers();

    int chanceStatusById(@Param("id") Integer id, @Param("status") String status);

    int insertCustomer(CrmCustomer customer);

    int updateCustomer(CrmCustomer customer);
}