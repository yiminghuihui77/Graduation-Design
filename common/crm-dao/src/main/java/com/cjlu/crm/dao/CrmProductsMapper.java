package com.cjlu.crm.dao;

import com.cjlu.crm.domain.CrmProducts;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmProductsMapper {
    CrmProducts selectByPrimaryKey(Integer id);
}