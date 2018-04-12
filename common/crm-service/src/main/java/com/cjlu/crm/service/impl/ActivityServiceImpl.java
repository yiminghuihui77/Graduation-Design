package com.cjlu.crm.service.impl;

import com.cjlu.crm.dao.CrmActivityMapper;
import com.cjlu.crm.domain.CrmActivity;
import com.cjlu.crm.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author minghui.y
 * @create 2018-04-11 21:04
 **/
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private CrmActivityMapper activityMapper;

    @Override
    public List<CrmActivity> queryByCustId(Integer id) {
        return activityMapper.selectByCustId(id);
    }
}
