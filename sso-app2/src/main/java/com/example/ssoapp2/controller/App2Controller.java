package com.example.ssoapp2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author :hezhiqiang06
 * @Date :2019-07-25 11:41
 **/


@Controller
public class App2Controller {

    @GetMapping("/app2")
    @PreAuthorize("hasAuthority('ROLE_NORMAL')")
    public String app2() {
        return "app2";
    }
}
