package com.cjlu.crm.service;

import com.cjlu.crm.domain.CrmDevelop;

import java.util.List;

/**
 * 客户开发服务
 *
 * @author minghui.y
 * @create 2018-04-14 20:49
 **/
public interface DevelopService {
    List<CrmDevelop> queryAllDevelops();
    int removeDevelopById(Integer id);
    int addDevelop(CrmDevelop develop);
    int refreshDevelop(CrmDevelop develop);
}
