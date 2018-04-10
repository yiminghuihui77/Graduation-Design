package com.cjlu.crm.configurations;

import com.cjlu.crm.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Web配置类
 *
 * @author minghui.y
 * @create 2018-03-28 11:58
 **/
//@Configuration
public class WebConfiguration {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(loginFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter("paramName", "paramValue");
        registrationBean.setName("loginFilter");

        return registrationBean;
    }

    @Bean(name = "loginFilter")
    public LoginFilter loginFilter() {
        return new LoginFilter();
    }
}
