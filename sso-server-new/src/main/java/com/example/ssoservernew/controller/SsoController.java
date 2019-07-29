package com.example.ssoservernew.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SsoController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @ResponseBody
    @GetMapping("/login-success")
    public String success(){
        return "login-success";
    }

    @ResponseBody
    @GetMapping("/login-error")
    public String error(){
        return "Error!";
    }

}
