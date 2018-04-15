package com.cjlu.crm.controller;

import com.cjlu.crm.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户流失控制器
 *
 * @author minghui.y
 * @create 2018-04-15 13:13
 **/
@RestController
@RequestMapping("/api")
public class LossController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LossController.class);

    @RequestMapping("/queryAllLoss")
    public Result queryAllLoss() {

        return null;
    }
}
