package com.cjlu.crm.notify.Impl;

import com.cjlu.crm.notify.DingDingRobotTunnel;
import com.cjlu.crm.notify.NotifyService;
import com.cjlu.crm.notify.domain.NotifyContent;
import com.cjlu.crm.notify.domain.NotifyResult;
import org.springframework.stereotype.Service;

/**
 * @author minghui.y
 * @create 2018-05-15 15:53
 **/
@Service
public class NotifyServiceImpl implements NotifyService {


    @Override
    public NotifyResult notifyToDingDing(NotifyContent content, String... tokens) {
        if (content == null || tokens == null) {
            return new NotifyResult();
        }
        //任务执行结果
        NotifyResult notifyResult = new NotifyResult();
        //针对每一个token执行发送任务
        for (String token : tokens) {
            notifyResult.add(token, DingDingRobotTunnel.send(content, token));
        }
        return notifyResult;
    }
}
