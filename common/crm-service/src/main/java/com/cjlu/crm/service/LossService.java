package com.cjlu.crm.service;

import com.cjlu.crm.domain.CrmLoss;

import java.util.List;

/**
 * 客户流失服务
 *
 * @author minghui.y
 * @create 2018-04-16 17:52
 **/
public interface LossService {
    List<CrmLoss> queryAllLoss();
    int removeLoss(Integer id);
    int addLoss(CrmLoss loss);
    int refreshLoss(CrmLoss loss);
}
