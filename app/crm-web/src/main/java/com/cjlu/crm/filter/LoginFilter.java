package com.cjlu.crm.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录过滤器
 *
 * @author minghui.y
 * @create 2018-03-28 10:29
 **/
//@Order(1)
//@WebFilter(filterName = "loginFilter",urlPatterns = "/*")
public class LoginFilter implements Filter {

    private List<String> ignoreUrl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ignoreUrl = new ArrayList<>();
        ignoreUrl.add("auth.json");
        ignoreUrl.add("query.json");
        ignoreUrl.add("freemark");
        //调试
        System.out.println("登录过滤器被初始化...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //调试
        System.out.println("登录过滤器被执行...");

        //以下条件判断是否要放行
        //1、判断Session域中是否存在登录用户的信息，若存在直接放行
        if (request.getSession().getAttribute("user") != null) {
            filterChain.doFilter(request, response);
            return;
        }
        //2、静态资源直接放行
        String uriPath = request.getRequestURI().toLowerCase();
        if (uriPath.matches("[\\w\\/-]+\\.(css|js|jpg|jpeg|png|gif|jar|ico|htc|swf|otf|eot|svg|ttf|woff|woff2)")) {
            filterChain.doFilter(request, response);
            return;
        }
        //3、指定忽略的url直接放行
        for (String uri : ignoreUrl) {
            if (uriPath.contains(uri)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        //拦截的情况：直接重定向到登录界面
        response.sendRedirect(request.getContextPath() + "/login.html");

    }

    @Override
    public void destroy() {

    }
}
