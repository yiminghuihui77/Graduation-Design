package com.cjlu.crm.test.sms;

import com.alibaba.fastjson.JSONObject;
import com.cjlu.crm.constants.SmsBizType;
import com.cjlu.crm.notify.SmsRobotTunnel;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author minghui.y
 * @create 2018-05-16 13:02
 **/
public class SmsServiceTest {

    @Test
    public void testDemo() {
        String phones = "17826833394";
//        String content = "{'custName':'张三','dueName':'王经理'}";
        Map<String, String> infos = new HashMap<>();
        infos.put("custName", "张三");
        infos.put("dueName", "王经理");
        String content = JSONObject.toJSONString(infos);
       System.out.println("运行结果：" +  SmsRobotTunnel.send(content, phones, SmsBizType.SMS_CREATE_SERVICE));
    }

    @Test
    public void testDemo2() {
        Map<String, String> infos = new HashMap<>();
        infos.put("custName", "张三");
        infos.put("dueName", "王经理");
        String jsonStr = JSONObject.toJSONString(infos);
        System.out.println(jsonStr);
    }
}
