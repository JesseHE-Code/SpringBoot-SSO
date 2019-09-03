package com.example.ssoapp1.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author :hezhiqiang06
 * @Date :2019-07-25 11:37
 **/


@Controller
public class App1Controller {

    @GetMapping("/app1")
    @PreAuthorize("hasAuthority('ROLE_NORMAL')")
    public String app1(){
        return "app1";
    }
}
