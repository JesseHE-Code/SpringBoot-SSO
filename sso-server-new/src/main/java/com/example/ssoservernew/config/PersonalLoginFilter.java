package com.example.ssoservernew.config;

import com.example.ssoservernew.handler.MyFailHandler;
import com.example.ssoservernew.handler.MySuccessHandler;
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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
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
        super.setAuthenticationSuccessHandler(new MySuccessHandler());
        super.setAuthenticationFailureHandler(new MyFailHandler());
        SimpleUrlAuthenticationSuccessHandler successHandler = (SimpleUrlAuthenticationSuccessHandler)getSuccessHandler();
        successHandler.setDefaultTargetUrl("/success");
        logger.info("PersonalLoginFilter---------------");
        logger.info(successHandler.toString());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException{

        String redirectUrl = obtainRedercitUrl(httpServletRequest);

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

        /*if(username == null | password == null){
            throw new UserPrincipalNotFoundException("用户名和密码错误");
        }*/
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);
        logger.info("生成UsernamePasswordAuthenticationToken:");
        logger.info(authRequest.toString());

        logger.info("回调地址");
        logger.info(redirectUrl);

        //自定义回调URL，若存在则放入Session
        if(redirectUrl != null && !"".equals(redirectUrl)){
            httpServletRequest.getSession().setAttribute("callCustomRediretUrl", redirectUrl);
        }

        setDetails(httpServletRequest, authRequest);

        logger.info(this.getAuthenticationManager().toString());

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainRedercitUrl(HttpServletRequest request) {
        return request.getParameter("spring-security-redirect");
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
