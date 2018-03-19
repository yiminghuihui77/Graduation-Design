package com.cjlu.crm.controller;

import com.cjlu.crm.domain.CrmActivity;
import com.cjlu.crm.service.CrmActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 第一次运行SpringBoot项目
 *
 * @author minghui.y
 * @create 2018-03-16 20:51
 **/
@RestController
@EnableAutoConfiguration
public class HelloController {

    @Autowired
    private CrmActivityService activityService;

    @RequestMapping("/")
    public String home() {
        return "Hello World! Welcome to SpringBoot.";
    }

    @RequestMapping("/queryActivity")
    public String queryActiviry() {
        CrmActivity activity = activityService.queryById(1);
        if (activity != null) {
            return activity.toString();
        }
        return "nothing is available.";
    }


}
