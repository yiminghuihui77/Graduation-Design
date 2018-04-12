package com.cjlu.crm.service;

import com.cjlu.crm.domain.CrmActivity;

import java.util.List;

/**
 * 交往记录服务
 *
 * @author minghui.y
 * @create 2018-04-11 21:03
 **/
public interface ActivityService {
    List<CrmActivity> queryByCustId(Integer id);
}
