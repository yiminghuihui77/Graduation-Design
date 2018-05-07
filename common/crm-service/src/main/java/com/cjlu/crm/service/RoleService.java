package com.cjlu.crm.service;

import com.cjlu.crm.domain.CrmRole;

/**
 * 角色服务接口
 *
 * @author minghui.y
 * @create 2018-05-07 17:08
 **/
public interface RoleService {
    CrmRole queryRoleById(Integer id);
}
