package com.cjlu.crm.controller;

import com.cjlu.crm.constants.SysCodeEnum;
import com.cjlu.crm.domain.Result;
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

    @RequestMapping("/auth.json")
    public Result loginAuth(HttpServletRequest request,
                            @RequestParam(value = "account") String account,
                            @RequestParam(value = "password") String password) {
        System.out.println("前端访问登录控制器！");
        //调用相关服务从数据库中查询用户是否存在

        //向前端返回登录结果信息

        Map<Object, Object> data = new HashMap<>();
        data.put("status", "success");
        data.put("array", Arrays.asList("java", "servlet", "PHP"));
        return new Result<Map>(SysCodeEnum.OK.getValue(), data);
    }

    @RequestMapping("/query.json")
    public Result queryInfo(@RequestParam("info") String info) {
        System.out.println("接收到的参数：" + info);
        return new Result<String>(SysCodeEnum.OK.getValue(),"query info success!");
    }
}