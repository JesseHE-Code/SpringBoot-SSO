package com.example.ssoservernew.controller;

import com.example.ssoservernew.dao.UserInfo;
import com.example.ssoservernew.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SsoController {

    @Autowired
    private UserServiceImpl userService;

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

    @ResponseBody
    @PostMapping("/register")
    public String register(@RequestParam("name") String userName, @RequestParam("pw") String password,
                           @RequestParam("rol") String role){
        role = role.toUpperCase();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setPassword(password);
        userInfo.setRole(role);
        int states = userService.insertUser(userInfo);
        if(1 == states){
            return "insert OK!";
        }
        else if(2 == states){
            return "insert error!";
        }
        else {
            return "User is in table";
        }
    }

}
