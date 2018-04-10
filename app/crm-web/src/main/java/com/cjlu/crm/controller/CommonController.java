package com.cjlu.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author minghui.y
 * @create 2018-04-05 23:57
 **/
@Controller
public class CommonController {

    @RequestMapping("/freemark")
    public String getName(Map<String,Object> map) {
        map.put("name", "ymh");
        return "home";
    }
}
