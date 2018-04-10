package com.cjlu.crm.service.impl;

import com.cjlu.crm.dao.CrmCustomerMapper;
import com.cjlu.crm.domain.CrmCustomer;
import com.cjlu.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author minghui.y
 * @create 2018-04-07 22:00
 **/
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CrmCustomerMapper customerMapper;

    @Override
    public CrmCustomer queryById(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }
}
