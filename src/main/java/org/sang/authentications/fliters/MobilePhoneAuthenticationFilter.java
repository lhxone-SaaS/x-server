package org.sang.authentications.fliters;

import org.sang.authentications.tokens.MobilePhoneAuthenticationToken;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 自定义手机登录认证过滤器
 */
public class MobilePhoneAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_PHONE_NO_KEY = "phoneNo";

    public static final String SPRING_SECURITY_PHONE_CODE_KEY = "phoneCode";

    private String phoneNoParameter = SPRING_SECURITY_PHONE_NO_KEY;

    private String phoneCodeParameter = SPRING_SECURITY_PHONE_CODE_KEY;

    private boolean postOnly = true;

    public MobilePhoneAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public MobilePhoneAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + httpServletRequest.getMethod());
        }
        String phoneNo = obtainPhoneNo(httpServletRequest);
        if (phoneNo==null) {
            phoneNo = "";
        } else {
            phoneNo = phoneNo.trim();
        }
        String phoneCode = obtainPhoneCode(httpServletRequest);
        if (phoneCode==null) {
            phoneCode = "";
        } else {
            phoneCode = phoneCode.trim();
        }
        MobilePhoneAuthenticationToken authRequest = new MobilePhoneAuthenticationToken(new ArrayList<>(), phoneNo, phoneCode);
        this.setDetails(httpServletRequest, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Nullable
    protected String obtainPhoneNo(HttpServletRequest request) {
        return request.getParameter(phoneNoParameter);
    }

    @Nullable
    protected String obtainPhoneCode(HttpServletRequest request) {
        return request.getParameter(phoneCodeParameter);
    }

    protected void setDetails(HttpServletRequest request, MobilePhoneAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
