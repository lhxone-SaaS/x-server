package org.sang.config;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class SmsConfig {

    @Resource
    private SmsProperty smsProperty;

    @Bean
    public SmsClient smsClient() {
        Credential cred = new Credential(smsProperty.getSecretId(), smsProperty.getSecretKey());
        // 实例化一个 http 选项，可选，无特殊需求时可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setConnTimeout(60);
        /** 设置代理
         httpProfile.setProxyHost("http://134.175.187.61");
         httpProfile.setProxyPort(8088);
         */
        /* SDK 默认使用 POST 方法。
         * 如需使用 GET 方法，可以在此处设置，但 GET 方法无法处理较大的请求 */
        httpProfile.setEndpoint(smsProperty.getEndpoint());
        /* 非必要步骤:
         * 实例化一个客户端配置对象，可以指定超时时间等配置 */
        ClientProfile clientProfile = new ClientProfile();
        /* SDK 默认用 TC3-HMAC-SHA256 进行签名
         * 非必要请不要修改该字段 */
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);
        SmsClient client = new SmsClient(cred, smsProperty.getRegion(),clientProfile);
        return client;
    }

}
