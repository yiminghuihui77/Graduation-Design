package com.cjlu.crm.controller;

import com.cjlu.crm.alipay.AlipayLoginService;
import com.cjlu.crm.domain.AlipayUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 支付宝第三方登录服务
 *
 * @author minghui.y
 * @create 2018-05-22 14:05
 **/
@RestController
@RequestMapping("/alipay")
public class AlipayController {

    @Autowired
    private AlipayLoginService alipayLoginService;

    @RequestMapping("/getAuthInfo.json")
    public String getAuthCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("支付宝返回信息....................................................");
        //从request中获取授权信息
        String authCode = request.getParameter("auth_code");
        String appID = request.getParameter("app_id");
        String scope = request.getParameter("scope");

        if (StringUtils.isNotEmpty(authCode)) {
            //获取access_token
            String accessToken = alipayLoginService.getAccessToken(authCode);
            //获取用户信息
            if (StringUtils.isNotEmpty(accessToken)) {
                //获取用户信息
                AlipayUser alipayUser = alipayLoginService.getUserInfoByToken(accessToken);

                //存储到cookie中
                Cookie cookieName = new Cookie("account", alipayUser.getNickName());
                Cookie cookieRole = new Cookie("roleName", "支付宝账户");
                cookieName.setMaxAge(3600);
                cookieRole.setMaxAge(3600);
                cookieName.setPath("/");
                cookieRole.setPath("/");
                response.addCookie(cookieName);
                response.addCookie(cookieRole);
                //跳转至主界面
                response.sendRedirect("http://106.14.149.152:80/");
            }
        }

        return "hello alipay!";
    }
}
