package com.example.ssoservernew.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component

public class SheepUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        if(!"hezhiqiang".equals(name)){
            throw new UsernameNotFoundException("User:" + name + "--->Not Found!");
        }
        return new User(name, passwordEncoder.encode("45678"), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_NORMAL, ROLE_MEDIUM"));
    }
}

