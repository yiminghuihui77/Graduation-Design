package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmProducts;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmProductsMapper {
    CrmProducts selectByPrimaryKey(Integer id);

    List<CrmProducts> selectAllProducts();
}