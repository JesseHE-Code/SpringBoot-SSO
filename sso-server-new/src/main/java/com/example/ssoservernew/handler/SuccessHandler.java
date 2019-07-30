package com.example.ssoservernew.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author :hezhiqiang06
 * @Date :2019-07-30 18:09
 **/

public class SuccessHandler implements AuthenticationSuccessHandler {

    private final static Logger logger = LoggerFactory.getLogger(SuccessHandler.class);
    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication){
        //可以在认证成功后获取到一些信息，并返回到响应的位置
        logger.info("onAuthenticationSuccess-------logger-----------");
        logger.info(httpServletRequest.toString());
        logger.info(httpServletRequest.getMethod());
        logger.info(httpServletRequest.getContextPath());
        logger.info(httpServletRequest.getServletPath());

    }
}
