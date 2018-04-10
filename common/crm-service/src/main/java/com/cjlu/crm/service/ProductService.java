package com.cjlu.crm.service;

import com.cjlu.crm.domain.CrmProducts;

import java.util.List;

/**
 * 产品服务
 *
 * @author minghui.y
 * @create 2018-04-07 22:03
 **/
public interface ProductService {
    CrmProducts queryById(Integer id);
    List<CrmProducts> queryAllProducts();
}
