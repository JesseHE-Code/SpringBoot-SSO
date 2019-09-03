package com.example.ssoservernew.config;

import com.example.ssoservernew.handler.MyFailHandler;
import com.example.ssoservernew.handler.MySuccessHandler;
import com.example.ssoservernew.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
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
 * @Date :2019-09-01 21:53
 **/

public class FaceLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private UserServiceImpl userService;

    FaceLoginFilter(){
        super(new AntPathRequestMatcher("/faceLoginHandel", HttpMethod.POST.name()));
        super.setAuthenticationSuccessHandler(new MySuccessHandler());
        super.setAuthenticationFailureHandler(new MyFailHandler());
        SimpleUrlAuthenticationSuccessHandler successHandler = (SimpleUrlAuthenticationSuccessHandler)getSuccessHandler();
        successHandler.setDefaultTargetUrl("/success");
        logger.info("PersonalLoginFilter---------------");
        logger.info(successHandler.toString());

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(!request.getMethod().equals(HttpMethod.POST.name()))
        {
            throw new AuthenticationServiceException("Authentication method not supported:\" "+ request.getMethod() + "\"");
        }


        String userFaceID = request.getParameter("username");
        //userFaceID = "jessehe";

        FaceLoginToken authRequest = new FaceLoginToken(userFaceID);

        logger.info("生成UsernamePasswordAuthenticationToken:");
        logger.info(authRequest.toString());

        setDetails(request, authRequest);

        //logger.info(this.getAuthenticationManager().toString());

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     *
     * @param request
     * @param authRequest
     */
    public void setDetails(HttpServletRequest request,
                           FaceLoginToken authRequest){
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
