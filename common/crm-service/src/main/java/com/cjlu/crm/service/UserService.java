package com.cjlu.crm.service;

import com.cjlu.crm.domain.CrmUser;

import java.util.List;

/**
 * 用户服务
 *
 * @author minghui.y
 * @create 2018-04-11 16:35
 **/
public interface UserService {
    CrmUser queryUserById(Integer id);
    List<CrmUser> queryAllManagers();
    List<CrmUser> queryAllSaleCharges();
}
