package org.sang.authentications.handlers;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class FormLoginFailedHandler implements AuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(FormLoginFailedHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        logger.error(" user login failed, caused by " + e.getMessage(),e);
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("status", "error");
        errorMap.put("msg", "login failed");
        errorMap.put("cause", e.getMessage());
        out.write(JSON.toJSONString(errorMap));
        out.flush();
        out.close();
    }
}
