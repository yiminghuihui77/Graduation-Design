package com.cjlu.crm.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.cjlu.crm.domain.AlipayUser;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * 支付宝第三方登录
 *
 * @author minghui.y
 * @create 2018-05-22 13:10
 **/
@Service
public class AlipayLoginService implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlipayLoginService.class);

    /**Alipay客户端*/
    private AlipayClient alipayClient;

    /**支付宝网关*/
    private static final String ALIPAY_BORDER_DEV = "https://openapi.alipaydev.com/gateway.do";
    private static final String ALIPAY_BORDER_PROD = "https://openapi.alipay.com/gateway.do";
    /**appID**/
    private static final String APP_ID_DEV = "2016091400507551";
    private static final String APP_ID_PROD = "2018052260191071";
    /**私钥*/
    private static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDIOyTB5xkSUnED6JcMysq3UepBYPnpfgt/6m4jiWT0Qcbjc52LIpQ20mvR7WN/nWQfP3AmHLNe5QuhcyQRi8XNGYMNdB68JEI+ltc7t91EDPBd9JV/haRg6Ht5QBszkT5mXyje1VRYaYNpH/cfGJIlSMHjsOyeTm+DqpQ1+lAj5zKk/BFr20l775qpAYaQp6/yCbnvLYjWDLEjTX4vMwgt25I2BT/caip6Sc5wMbAgKpW55z4AQa4CJGazQsbDh4iTnJ9ZdVNZCtFb+9cgqbcaAh1CoH/OYflVDpDod/poQrZXk0lzfLFqDD1Oap8miEmPsCzP6HjmrZ0t+Y/lMGrHAgMBAAECggEBAIprqJuLoV9zMsHa9yFWyBE3RdRwsBPkmRqS/BUBFJNvLYlsEctv2jqT+QmlLJW6Ps8DUgpm6PF/Mi0ophSjJdkF+/0++0hhYJJYQO79yq8SqtCpFviTcPG1hjZqROO/Uv3+Zwi1PZvpRXlVKceLeuzfgXpggxNlcd35/H/cTa6jGls8jHFgFzGiuOs60W1vFbPBhuFU7uLQd0C7OZH3+O3POskyB0EwfLJkqhjUlVvc2ETc3w1SOMjJttb08SedjbLyZauTfilxq3WQoq+jtuOxDOIRbakHBSTotFwj1Kbx47E0pZaOJy4ldytD4rwFbDAnMUoaszqXdkOzTuc5gpECgYEA8HuUQMhrxY/zeUI7Q59PJLDlXrDOHECm+l125q8dV5/sTT9XZkGBQdBId5TpsaGJdFDbKWtqA8BtjixT/s6EBX2o1yIVN3/cmhFerb3nmViwNGEgWFGO5uNqReruw6WpWr3a6S9OYyh6Vlrj7bV2BQ2+BBoC/AIoxUigcNaVRkUCgYEA1SaqY/3fuvhfBZQu3tlW/UPKcU88G4X/cK17ZyML9z40p1vAAhEedvAV/p8q8DgTmM5r4P3X74l46zcMNUSRem0pOICk40JhwSIcX/TX2E2QmqB8DbUk/dTXmnFEi4ND/Kqc+mSX6hU+eKOFWSuuyMmTIfTgZ/1sGcvd8BCU05sCgYBfniNiZs5DLWt3iVgbGRrU0elBbzdkkGhUIBmIu5QFJTdFt6u+oskMLA7ETVdmI36N2yBpJaZve3GUGBgFtoNT05TwhUK+YH6MW9+zYVDW7iaRjRTZSQRy1XWJ+1GYRSRDSj3ifcbDMnMCJv19pKsac05cPyN0pD8lwizY8NlFqQKBgG0lgoT3YFRmkInTynh0xJWl3AlPsJDLefUh8JnsmVj+somsctLcH0R6Rlj++kbOAEL0GMu6UJOpSpDvvEGenfwZ38y+GzEAMfxq0irUvp5VgaGcYknGo22TSuAb9iSWj/7XAtE5J41HWZj+6u6xbqwEhGIT+cFmRK+k9D3Uz9DXAoGAb8nLaeJn9FT71HQmiHdCnDiOtgkiePNWGIP6D8Lm/MZZuuq2i7a93a6B8jZjnDPa/PNxhpNZF+MTd7s8eIe72yFdFNcSWNvyKnxeIuAdhxuYeWcbFShTgLdZPDIVS8IJRfX5R/uWQJ/Q78/u/ao2UD0udBIl/gn6B5Q2DiqdvoE=";
    /**公钥*/
    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoEzBxDQfEKKMajCR0UHz89XCUWxWPu2rOJYW8l6SwtN9+lDjIvdAwd7AdERufIVt1Zd7Ruu4xDokX3fVaS6kpKscPUJ6lF8v4jBeHenvWQC9GnXfohy20SFrmueU3LLs4p/bniPE2IEOOodhW8OobvAUhJwReg/8bN1N6hyj/JHsgSSmhnM+ApgvW15VT/vteJ/GECt/AS4e+lPl6jszi9A49z4mW5optp6lr/uLGSWj55nTW/1mtoMR+/qp38YvC3YyHIpgwRLK/tXyTg5Z8kvKL1c9sOTEN39HzoJJzxMuLAWbyzqDeBTfZx2HTifdEfp3n0VPUhKWSMojGHuUKwIDAQAB";

    @Override
    public void afterPropertiesSet() throws Exception {
        alipayClient = new DefaultAlipayClient(ALIPAY_BORDER_PROD, APP_ID_PROD, APP_PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");
    }

    /**
     * 根据auth_code获取用户的user_id和access_token
     * @param authCode
     * @return
     */
    public String getAccessToken(String authCode) {
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(authCode);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            return oauthTokenResponse.getAccessToken();
        } catch (Exception e) {

            LOGGER.error("使用authCode获取信息失败！", e);
            return null;
        }
    }

    /**
     * 根据access_token获取用户信息
     * @param token
     * @return
     */
    public AlipayUser getUserInfoByToken(String token) {
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest ();
        try {
            AlipayUserInfoShareResponse response =  alipayClient.execute(request, token);
            if (response.isSuccess()) {
                //打印响应信息
//                System.out.println(ReflectionToStringBuilder.toString(response));
                //封装支付宝对象信息
                AlipayUser alipayUser = new AlipayUser();
                alipayUser.setAddress(response.getAddress());
                alipayUser.setCertNo(response.getCertNo());
                alipayUser.setCity(response.getCity());
                alipayUser.setCollegeName(response.getCollegeName());
                alipayUser.setDegree(response.getDegree());
                alipayUser.setMobile(response.getMobile());
                alipayUser.setPhone(response.getPhone());
                alipayUser.setProvince(response.getProvince());
                alipayUser.setUserName(response.getUserName());
                alipayUser.setNickName(response.getNickName());
                return alipayUser;
            }
            LOGGER.error("根据 access_token获取用户信息失败!");
            return null;

        } catch (Exception e) {
            LOGGER.error("根据 access_token获取用户信息抛出异常！", e);
            return null;
        }
    }
}
