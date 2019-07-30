package com.example.ssoservernew.config;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author :hezhiqiang06
 * @Date :2019-07-30 11:23
 **/

@Getter
@Setter
public class PersonalLoginFilter extends AbstractAuthenticationProcessingFilter {

    //Logger
    private final static Logger logger = LoggerFactory.getLogger(PersonalLoginFilter.class);

    private boolean isPost = true;

    PersonalLoginFilter(String defaultFilterProcessesUrl){
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException{
        //获取用户名和密码，进行权限判定
        //生成认证
        logger.info("进入自定义的请求过滤器-------！");
        //Step1:判断是否为POST请求
        if(isPost & !httpServletRequest.getMethod().equals(HttpMethod.POST.name()))
        {
            throw new AuthenticationServiceException("Authentication method not supported:\" "+ httpServletRequest.getMethod() + "\"");
        }
        logger.info("请求方式正常---------！");
        //Step2:获取提交的用户名和密码
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);
        logger.info("生成token:");
        logger.info(authRequest.toString());
        logger.info("开始校验用户名和密码");
        setDetails(httpServletRequest, authRequest);

        logger.info(this.getAuthenticationManager().toString());

        return this.getAuthenticationManager().authenticate(authRequest);
    }


    /**
     *
     * @param request
     * @param authRequest
     */
    public void setDetails(HttpServletRequest request,
                           UsernamePasswordAuthenticationToken authRequest){
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
