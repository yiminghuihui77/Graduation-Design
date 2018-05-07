package com.cjlu.crm.service.impl;

import com.cjlu.crm.dao.CrmRoleMapper;
import com.cjlu.crm.domain.CrmRole;
import com.cjlu.crm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author minghui.y
 * @create 2018-05-07 17:18
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private CrmRoleMapper roleMapper;

    @Override
    public CrmRole queryRoleById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }
}
