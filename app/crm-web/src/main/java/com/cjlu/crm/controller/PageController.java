package com.cjlu.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 页面控制器
 *
 * @author minghui.y
 * @create 2018-04-05 21:40
 **/
@Controller
public class PageController {

    @RequestMapping("")
    public String handleDefault(HttpServletRequest request, Model model) {
        return handle(request, model);
    }

    @RequestMapping("/**/*.html")
    public String handle(HttpServletRequest request, Model model) {
        if (request.getRequestURI().toLowerCase().contains("login.html")
                && request.getSession().getAttribute("user")  == null) {
            return "login";
        }
        return "index";
    }
}
