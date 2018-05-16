package com.cjlu.crm.service.impl;

import com.cjlu.crm.dao.CrmServiceMapper;
import com.cjlu.crm.domain.CrmService;
import com.cjlu.crm.service.ServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author minghui.y
 * @create 2018-04-17 17:25
 **/
@Service
public class ServeServiceImpl implements ServeService {

    @Autowired
    private CrmServiceMapper serviceMapper;

    @Override
    public int createService(CrmService service) {
        return serviceMapper.insertService(service);
    }

    @Override
    public List<CrmService> queryAllService() {
        return serviceMapper.selectAllService();
    }

    @Override
    public int refreshRemarkById(Integer id, String remark) {
        return serviceMapper.updateRemarkById(id, remark);
    }

    @Override
    public List<CrmService> queryArchiveService() {
        return serviceMapper.selectArchiveService();
    }

    @Override
    public int archiveService(Integer id, Integer starLevel) {
        return serviceMapper.archiveService(id, starLevel);
    }

    @Override
    public CrmService queryById(Integer id) {
        return serviceMapper.selectByPrimaryKey(id);
    }
}
