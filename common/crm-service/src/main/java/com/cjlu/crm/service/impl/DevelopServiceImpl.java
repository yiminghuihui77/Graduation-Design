package com.cjlu.crm.service.impl;

import com.cjlu.crm.dao.CrmDevelopMapper;
import com.cjlu.crm.domain.CrmDevelop;
import com.cjlu.crm.service.DevelopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author minghui.y
 * @create 2018-04-14 20:53
 **/
@Service
public class DevelopServiceImpl implements DevelopService {

    @Autowired
    private CrmDevelopMapper developMapper;

    @Override
    public List<CrmDevelop> queryAllDevelops() {
        return developMapper.selectAllDevelops();
    }

    @Override
    public int removeDevelopById(Integer id) {
        return developMapper.deleteDevelopById(id);
    }

    @Override
    public int addDevelop(CrmDevelop develop) {
        return developMapper.insertDevelop(develop);
    }

    @Override
    public int refreshDevelop(CrmDevelop develop) {
        return developMapper.updateDevelop(develop);
    }
}
