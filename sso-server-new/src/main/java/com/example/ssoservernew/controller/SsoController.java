package com.example.ssoservernew.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SsoController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
