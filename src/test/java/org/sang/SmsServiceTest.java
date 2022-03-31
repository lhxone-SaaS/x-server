package org.sang;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import org.junit.jupiter.api.Test;
import org.sang.enums.SmsEnum;

import org.sang.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Random;


@SpringBootTest
public class SmsServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(SmsServiceTest.class);

    @Resource
    private SmsService smsService;

    public static void main(String[] args) {

        String numbers = "1234567890";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for(int i=0; i<6; i++) {
            int index = random.nextInt(10);
            builder.append(numbers.charAt(index));
        }
        String code = builder.toString();
        System.out.println("code="+code);
        Credential cred = new Credential("AKIDg9azAImoUwMeLBPBKSSClCbolbaM2nja", "rbN95PZfW9Yt2uhAattdsqYFqR2QM6eB");
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
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        /* 非必要步骤:
         * 实例化一个客户端配置对象，可以指定超时时间等配置 */
        ClientProfile clientProfile = new ClientProfile();
        /* SDK 默认用 TC3-HMAC-SHA256 进行签名
         * 非必要请不要修改该字段 */
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);
        SmsClient client = new SmsClient(cred, "ap-guangzhou",clientProfile);
        SendSmsRequest req = new SendSmsRequest();
        req.setSenderId(null);
        req.setSessionContext(null);
        req.setSign("阿福谈Java技术栈");
        req.setSmsSdkAppid("1400491186");
        req.setTemplateID(SmsEnum.PHONE_CODE_LOGIN.getTemplateId());
        req.setPhoneNumberSet(new String[]{"+86-18682244076"});
        String[] params = new String[]{code, String.valueOf(10)};
        req.setTemplateParamSet(params);
        try {
            SendSmsResponse res = client.SendSms(req);
            System.out.println("requestId="+res.getRequestId());
            SendStatus[] statuses = res.getSendStatusSet();
            for(int i=0; i<statuses.length; i++) {
                System.out.println("i="+i);
                System.out.println("code="+statuses[i].getCode());
                System.out.println("message="+statuses[i].getMessage());
            }
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSendMessage() {
        SendSmsResponse response = smsService.sendLoginVeryCodeMessage("+86-18682244076");
        logger.info("response={}", SendSmsResponse .toJsonString(response));
    }

}
