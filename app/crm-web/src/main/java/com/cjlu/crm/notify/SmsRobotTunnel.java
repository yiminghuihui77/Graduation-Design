package com.cjlu.crm.notify;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.cjlu.crm.constants.SmsBizType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 阿里云短信服务机器人
 *
 * @author minghui.y
 * @create 2018-05-16 9:54
 **/
public class SmsRobotTunnel {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsRobotTunnel.class);

    /**短信API产品名称（短信产品名固定，无需修改）*/
    private static final String PRODUCT = "Dysmsapi";
    /**短信API产品域名（接口地址固定，无需修改）*/
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";
    /**AccessKeyId*/
    private static final String  ACCESSKEYID = "LTAImCY7u2QL8KHX";
    /**accessKeySecret*/
    private static final String ACCESSKEYSECRET = "e0saIcJdF0u4TvWQo7s2kVI9B4IKkL";
    /**短信签名*/
    private static final String SMSSIGN = "CRM系统";
    /**短信模板ID*/
    private static final String SMSTEMPLATE0 = "SMS_135035173";
    private static final String SMSTEMPLATE1 = "SMS_135035174";

    /**
     * 发送短信方法
     * @param content：JSON字串，替代短信模板中的变量
     * @param phones：可以使多个手机号，以逗号分隔
     * @return：发送成功则返回true
     */
    public static boolean send(String content, String phones, SmsBizType bizType) {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        try {
            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESSKEYID, ACCESSKEYSECRET);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phones);
            //必填:短信签名
            request.setSignName(SMSSIGN);
            //必填:短信模板
            String templcateCode = null;
            switch (bizType) {
                case SMS_CREATE_SERVICE:
                    templcateCode = SMSTEMPLATE0;
                    break;
                case SMS_PROCESS_SERVICE:
                    templcateCode = SMSTEMPLATE1;
                    break;
                default:
                    templcateCode = SMSTEMPLATE0;
                    break;
            }
            request.setTemplateCode(templcateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam(content);


            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            //解析短信响应结果
            if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                //请求成功
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("短信发送服务异常！", e);
        }

        return false;
    }

}
