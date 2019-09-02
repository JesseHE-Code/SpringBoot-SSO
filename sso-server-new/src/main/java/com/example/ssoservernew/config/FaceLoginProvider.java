package com.example.ssoservernew.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author :hezhiqiang06
 * @Date :2019-09-02 21:01
 **/

public class FaceLoginProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FaceLoginToken faceLoginToken = (FaceLoginToken) authentication;
        UserDetails userDetails = userDetailsService.loadUserByUsername((String)faceLoginToken.getPrincipal());
        if(userDetails == null || StringUtils.isEmpty(userDetails.getUsername())) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        FaceLoginToken authenticationResult = new FaceLoginToken(userDetails.getUsername(), userDetails.getAuthorities());
        authenticationResult.setDetails(faceLoginToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return FaceLoginToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
