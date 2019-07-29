package com.example.ssoapp1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Author :hezhiqiang06
 * @Date :2019-07-25 11:37
 **/


@Controller
@PropertySource("classpath:application.properties")

public class App1Controller {

    @ResponseBody
    @GetMapping("/normal")
    @PreAuthorize("hasAuthority('ROLE_NORMAL')")
    public String normal() {
        return "normal permission test success !!!";
    }

    @ResponseBody
    @GetMapping("/medium")
    @PreAuthorize("hasAuthority('ROLE_MEDIUM')")
    public String medium() {
        return "medium permission test success !!!";
    }

    @ResponseBody
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String admin() {
        return "admin permission test success !!!";
    }

    @GetMapping("/app1")
    public String app1(){
        return "app1";
    }
}
