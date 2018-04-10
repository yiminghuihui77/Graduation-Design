package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmCustomer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmCustomerMapper {
    CrmCustomer selectByPrimaryKey(Integer id);

    List<CrmCustomer> selectAllCustomers();
}