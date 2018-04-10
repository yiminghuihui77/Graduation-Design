package com.cjlu.crm.service;

import com.cjlu.crm.domain.CrmCustomer;

import java.util.List;

/**
 * 客户信息服务
 *
 * @author minghui.y
 * @create 2018-04-07 21:58
 **/
public interface CustomerService {
    CrmCustomer queryById(Integer id);
    List<CrmCustomer> queryAllCustomers();
}
