package com.cjlu.crm.service.impl;

import com.cjlu.crm.dao.CrmLossMapper;
import com.cjlu.crm.domain.CrmLoss;
import com.cjlu.crm.service.LossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author minghui.y
 * @create 2018-04-16 17:53
 **/
@Service
public class LossServiceImpl implements LossService {

    @Autowired
    private CrmLossMapper lossMapper;

    @Override
    public List<CrmLoss> queryAllLoss() {
        return lossMapper.selectAllLoss();
    }

    @Override
    public int removeLoss(Integer id) {
        return lossMapper.deleteLossById(id);
    }
}
