package com.cjlu.crm.service.impl;

import com.cjlu.crm.dao.CrmProductsMapper;
import com.cjlu.crm.domain.CrmProducts;
import com.cjlu.crm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author minghui.y
 * @create 2018-04-07 22:04
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CrmProductsMapper productsMapper;

    @Override
    public CrmProducts queryById(Integer id) {
        return productsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CrmProducts> queryAllProducts() {
        return productsMapper.selectAllProducts();
    }
}
