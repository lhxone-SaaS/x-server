package org.sang.service;

import com.alibaba.fastjson.JSON;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.sang.config.SmsProperty;
import org.sang.enums.SmsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
    @Resource
    private SmsProperty smsProperty;
    @Resource
    private SmsClient smsClient;
    @Resource
    private RedisTemplate redisTemplate;

    public SendSmsResponse sendLoginVeryCodeMessage(String phoneNum) {
        SendSmsRequest req = new SendSmsRequest();
        req.setSenderId(null);
        req.setSessionContext(null);
        req.setSign("阿福谈Java技术栈");
        req.setSmsSdkAppid(smsProperty.getAppid());
        req.setTemplateID(SmsEnum.PHONE_CODE_LOGIN.getTemplateId());
        req.setPhoneNumberSet(new String[]{phoneNum});
        String verifyCode = getCode();
        String[] params = new String[]{verifyCode, "10"};
        req.setTemplateParamSet(params);
        logger.info("req={}", JSON.toJSONString(req));
        try {
            SendSmsResponse res = smsClient.SendSms(req);
            if ("Ok".equals(res.getSendStatusSet()[0].getCode())) {
                // 截掉+86字段，发送短信验证码成功则将验证码保存到redis缓存中(目前只针对国内短息业务)
                phoneNum = phoneNum.substring(3);
                redisTemplate.opsForValue().set("loginVerifyCode:"+phoneNum, verifyCode, 10, TimeUnit.MINUTES);
            }
            return res;
        } catch (TencentCloudSDKException e) {
            logger.error("send message failed", e);
            throw new RuntimeException("send message failed, caused by " + e.getMessage());
        }
    }

    /**
     * 生成6位随机数字验证码
     * @return
     */
    private String getCode() {
        String numbers = "1234567890";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for(int i=0; i<6; i++) {
            int index = random.nextInt(10);
            builder.append(numbers.charAt(index));
        }
        return builder.toString();
    }
}
