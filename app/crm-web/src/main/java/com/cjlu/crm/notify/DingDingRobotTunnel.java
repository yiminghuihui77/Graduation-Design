package com.cjlu.crm.notify;

import com.alibaba.fastjson.JSONObject;
import com.cjlu.crm.notify.domain.NotifyContent;
import com.cjlu.crm.utils.HttpClientUtil;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 钉钉机器人
 *
 * @author minghui.y
 * @create 2018-05-15 16:28
 **/
public class DingDingRobotTunnel {
    private static final Logger LOGGER = LoggerFactory.getLogger(DingDingRobotTunnel.class);

    private static final String DD_WEB_HOOK_ADDR =  "https://oapi.dingtalk.com/robot/send?access_token=";

    public static boolean send(NotifyContent nofityContent, String token) {

        String content = nofityContent.getDingDingContent();

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        try {
            JSONObject response = JSONObject.parseObject(HttpClientUtil.doPost(DD_WEB_HOOK_ADDR + token, null, content, headers));
            if (response.containsKey("errcode") && "0".equals(response.getString("errcode"))) {
                return true;
            }

            throw new Exception(response.getString("errmsg"));
        } catch (Exception e) {
            LOGGER.error("钉钉机器人发送任务执行失败！token：" + token, e);
            return false;
        }
    }
}
