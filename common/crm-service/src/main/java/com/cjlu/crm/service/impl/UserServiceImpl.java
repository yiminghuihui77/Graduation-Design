package com.cjlu.crm.service.impl;

import com.cjlu.crm.dao.CrmUserMapper;
import com.cjlu.crm.domain.CrmUser;
import com.cjlu.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author minghui.y
 * @create 2018-04-11 16:36
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CrmUserMapper userMapper;

    @Override
    public CrmUser queryUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
