package com.cjlu.crm.notify;

import com.cjlu.crm.notify.domain.NotifyContent;
import com.cjlu.crm.notify.domain.NotifyResult;

/**
 * 钉钉群通知服务
 *
 * @author minghui.y
 * @create 2018-05-15 15:52
 **/
public interface NotifyService {
    NotifyResult notifyToDingDing(NotifyContent content, String... tokens);
}
