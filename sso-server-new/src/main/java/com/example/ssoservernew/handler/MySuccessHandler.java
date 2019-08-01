package com.example.ssoservernew.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

/**
 * @Author :hezhiqiang06
 * @Date :2019-07-30 18:09
 **/
@Component
public class MySuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final static Logger logger = LoggerFactory.getLogger(MySuccessHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        //可以在认证成功后获取到一些信息，并返回到响应的位置

        logger.info("onAuthenticationSuccess-------logger-----------");

        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);
        if(savedRequest == null){
            logger.info("saveRequest = NULL!");
            //用户判断是否要使用上次通过session里缓存的回调URL地址
            int flag = 0;
            //通过提交登录请求传递需要回调的URL callCustomRediretUrl
            if(httpServletRequest.getSession().getAttribute("callCustomRediretUrl") != null && !"".equals(httpServletRequest.getSession().getAttribute("callCustomRediretUrl"))) {
                String url = String.valueOf(httpServletRequest.getSession().getAttribute("callCustomRediretUrl"));
                //若session 存在则需要使用自定义回调的URL 而不是缓存的URL
                super.setDefaultTargetUrl(url);
                super.setAlwaysUseDefaultTargetUrl(true);
                flag = 1;
                httpServletRequest.getSession().setAttribute("callCustomRediretUrl", "");
            }
            if(flag == 0){
                super.setDefaultTargetUrl("/success");
            }
            super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
            return;
        }

        String callBackUrl = savedRequest.getRedirectUrl();
        logger.info("Call back URL:" + callBackUrl);


        logger.info("httpServletResponse");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        //获取session
        HttpSession session = httpServletRequest.getSession();
        /*Set some session variables*/
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("uname", authUser.getUsername());
        session.setAttribute("authorities", authentication.getAuthorities());

        logger.info(session.getServletContext().getContextPath());
        logger.info(session.getId());

        /*Set target URL to redirect*/
        //String targetUrl = determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, callBackUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (authorities.contains("ROLE_NORMAL")) {
            return "/success";
        } else if (authorities.contains("ROLE_USER")) {
            return "/user";
        } else {
            throw new IllegalStateException();
        }
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}
