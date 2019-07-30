package com.example.ssoservernew.service.impl;

import com.example.ssoservernew.dao.UserInfo;
import com.example.ssoservernew.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SsoUserDetailsServiceImpl implements UserDetailsService {

    private final static Logger logger = LoggerFactory.getLogger(SsoUserDetailsServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        logger.info("进入loadUserByUsername方法");
        UserInfo userInfo = userService.findUserByName(name);
        String userPassword = "noUser";
        if (userInfo == null){
            throw new UsernameNotFoundException("User:" + name + "--->Not Found!");
        }
        else
        {
            userPassword = userInfo.getPassword();
        }

        UserDetails userDetails= new User(name, passwordEncoder.encode(userPassword), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_"+userInfo.getRole()));
        logger.info("生成的UserDetails是：");
        logger.info(userDetails.toString());
        return userDetails;
    }
}

