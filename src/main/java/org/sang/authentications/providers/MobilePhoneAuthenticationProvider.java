package org.sang.authentications.providers;

import org.sang.authentications.tokens.MobilePhoneAuthenticationToken;

import org.sang.exception.PhoneNumberNotFoundException;
import org.sang.pojo.CustomUser;
import org.sang.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;


public class MobilePhoneAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {

    private UserService userService;

    private RedisTemplate redisTemplate;

    private boolean forcePrincipalAsString = false;

    private static final Logger logger = LoggerFactory.getLogger(MobilePhoneAuthenticationProvider.class);

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();

    /**
     * 带两个参数的构造方法
     * @param userService 用户信息查询服务
     * @param redisTemplate redis模板类
     */
    public MobilePhoneAuthenticationProvider(UserService userService, RedisTemplate redisTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 认证方法
     * @param authentication 认证token
     * @return successAuthenticationToken
     * @throws AuthenticationException 认证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 首先判断authentication参数必须是一个MobilePhoneAuthenticationToken类型对象
        Assert.isInstanceOf(MobilePhoneAuthenticationToken.class, authentication,
                ()-> this.messages.getMessage("MobilePhoneAuthenticationProvider.onlySupports", "Only MobilePhoneAuthenticationToken is supported"));
        // 获取authentication参数的principal属性作为手机号
        String phoneNo = authentication.getPrincipal().toString();
        if (StringUtils.isEmpty(phoneNo)) {
            logger.error("phoneNo cannot be null");
            throw new BadCredentialsException("phoneNo cannot be null");
        }
        // 获取authentication参数的credentials属性作为短信验证码
        String phoneCode = authentication.getCredentials().toString();
        if (StringUtils.isEmpty(phoneCode)) {
            logger.error("phoneCode cannot be null");
            throw new BadCredentialsException("phoneCode cannot be null");
        }
        try {
            // 调用userService服务根据手机号查询用户信息
            CustomUser user = (CustomUser) userService.loadUserByPhoneNum(Long.parseLong(phoneNo));
            // 校验用户账号是否过期、是否被锁住、是否有效等属性
            userDetailsChecker.check(user);
            // 根据手机号组成的key值去redis缓存中查询发送短信验证码时存储的验证码
            String storedPhoneCode = (String) redisTemplate.opsForValue().get("loginVerifyCode:"+phoneNo);
            if (storedPhoneCode==null) {
                logger.error("phoneCode is expired");
                throw new BadCredentialsException("phoneCode is expired");
            }
            // 用户登录携带的短信验证码与redis中根据手机号查询出来的登录认证短信验证码不一致则抛出验证码错误异常
            if (!phoneCode.equals(storedPhoneCode)) {
                logger.error("the phoneCode is not correct");
                throw new BadCredentialsException("the phoneCode is not correct");
            }
            // 把完成的用户信息赋值给组成返回认证token中的principal属性值
            Object principalToReturn = user;
            // 如果强制把用户信息转成字符串，则只返回用户的手机号码
            if(isForcePrincipalAsString()) {
                principalToReturn = user.getPhoneNum();
            }
            // 认证成功则返回一个MobilePhoneAuthenticationToken实例对象，principal属性为较为完整的用户信息
            MobilePhoneAuthenticationToken successAuthenticationToken = new MobilePhoneAuthenticationToken(user.getAuthorities(), principalToReturn, phoneCode);
            return successAuthenticationToken;
        } catch (UsernameNotFoundException e) {
            // 用户手机号不存在，如果用户已注册提示用户先去个人信息页面添加手机号码信息，否则提示用户使用手机号注册成为用户后再登录
            logger.error("user " + phoneNo + "not found, if you have been register as a user, please goto the page of edit user information to  add you phone number, " +
                    "else you must register as a user use you phone number");
            throw new BadCredentialsException("user " + phoneNo + "not found, if you have been register as a user, please goto the page of edit user information to  add you phone number, " +
                    "else you must register as a user use you phone number");
        } catch (NumberFormatException e) {
            logger.error("invalid phoneNo, due it is not a number");
            throw new BadCredentialsException("invalid phoneNo, due do phoneNo is not a number");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(MobilePhoneAuthenticationToken.class);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messages, "A message source must be set");
        Assert.notNull(this.redisTemplate, "A RedisTemplate must be set");
        Assert.notNull(this.userService, "A UserDetailsService must be set");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public void setForcePrincipalAsString(boolean forcePrincipalAsString) {
        this.forcePrincipalAsString = forcePrincipalAsString;
    }

    public boolean isForcePrincipalAsString() {
        return forcePrincipalAsString;
    }
}
