package com.example.ssoservernew.domain;

import com.example.ssoservernew.dao.UserInfo;
import com.example.ssoservernew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SsoUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        UserInfo userInfo = userService.findUserByName(name);
        String userPassword = "noUser";
        if (userInfo == null){
            throw new UsernameNotFoundException("User:" + name + "--->Not Found!");
        }
        else
        {
            userPassword = userInfo.getPassword();
        }

        return new User(name, passwordEncoder.encode(userPassword), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_"+userInfo.getRole()));
    }
}

