package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmCustomer;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmCustomerMapper {
    CrmCustomer selectByPrimaryKey(Integer id);
}