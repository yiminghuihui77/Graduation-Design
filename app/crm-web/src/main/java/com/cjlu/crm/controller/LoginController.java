package com.cjlu.crm.controller;

import com.cjlu.crm.constants.SysCodeEnum;
import com.cjlu.crm.domain.CrmUser;
import com.cjlu.crm.domain.Result;
import com.cjlu.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author minghui.y
 * @create 2018-03-24 16:25
 **/
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/auth.json")
    public Result loginAuth(HttpServletRequest request,
                            @RequestParam(value = "account") String account,
                            @RequestParam(value = "password") String password) {
        System.out.println("前端访问登录控制器！");
        //调用相关服务从数据库中查询用户是否存在
        CrmUser user = userService.queryByLogin(account, password);
        if (user == null) {
            return new Result<>(SysCodeEnum.OK.getValue(), "用户名或密码错误！");
        }

        //向前端返回登录结果信息
        Map<Object, Object> data = new HashMap<>();
        data.put("user", user);
        return new Result<Map>(SysCodeEnum.OK.getValue(), data);
    }

}
