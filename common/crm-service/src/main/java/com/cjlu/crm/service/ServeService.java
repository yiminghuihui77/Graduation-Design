package com.cjlu.crm.service;

import com.cjlu.crm.domain.CrmService;

import java.util.List;

/**
 * 服务管理服务
 *
 * @author minghui.y
 * @create 2018-04-17 17:24
 **/
public interface ServeService {
    int createService(CrmService service);
    List<CrmService> queryAllService();
    int refreshRemarkById(Integer id, String remark);
    List<CrmService> queryArchiveService();
    int archiveService(Integer id, Integer starLevel);
    CrmService queryById(Integer id);
}
