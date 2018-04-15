package com.cjlu.crm.controller;

import com.cjlu.crm.constants.SysCodeEnum;
import com.cjlu.crm.domain.CrmActivity;
import com.cjlu.crm.domain.Result;
import com.cjlu.crm.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 交往记录控制器
 *
 * @author minghui.y
 * @create 2018-04-15 12:53
 **/
@RestController
@RequestMapping("/api")
public class ActivityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/addActivity.json")
    public Result addActivity(CrmActivity activity) {
        LOGGER.info("新增交往记录--------");
        activity.setGmtCreated(new Date());
        activity.setGmtModified(new Date());
        //插入交往记录
        if (activityService.addActivity(activity) <= 0) {
            return new Result<>(SysCodeEnum.ERR_SYS.getValue(), "新增交往记录失败！");
        }
        return new Result<>(SysCodeEnum.OK.getValue(), "新增交往记录成功！");
    }

}
