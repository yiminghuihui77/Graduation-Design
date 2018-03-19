package com.cjlu.crm.service.impl;

import com.cjlu.crm.dao.CrmActivityMapper;
import com.cjlu.crm.domain.CrmActivity;
import com.cjlu.crm.service.CrmActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author minghui.y
 * @create 2018-03-17 0:25
 **/
@Service
public class ActivityServiceImpl implements CrmActivityService {

    @Autowired
    private CrmActivityMapper activityMapper;

    @Override
    public CrmActivity queryById(Integer id) {
        return activityMapper.selectByPrimaryKey(id);
    }
}
