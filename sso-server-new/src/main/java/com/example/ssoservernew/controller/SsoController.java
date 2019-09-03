package com.example.ssoservernew.controller;

import com.example.ssoservernew.dao.UserInfo;
import com.example.ssoservernew.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class SsoController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping(value={"/login"})
    public String login(){
        return "login";
    }

    @GetMapping(value={"/success", "/index"})
    public String success(){
        return "index";
    }

    @ResponseBody
    @GetMapping("/test")
    public String error(){
        return "SSO test";
    }

    @GetMapping(value={"/register"})
    public String register(){
        return "register";
    }

    /**
     *
     * @param userName
     * @param password
     * @param passwordc
     * @param model
     * @return 根据处理结果
     */
    @PostMapping("/registerapi")
    public String register_1(@RequestParam("username") String userName, @RequestParam("password") String password,
                             @RequestParam("passwordc")String passwordc, ModelMap model){
        String role = "normal";
        role = role.toUpperCase();

        if (!password.equals(passwordc)){
            model.addAttribute("error", true);
            model.addAttribute("errorInfo", "密码不匹配");
            model.addAttribute("username", userName);
            return "register";
        }
        else {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(userName);
            userInfo.setPassword(password);
            userInfo.setRole(role);
            int states = userService.insertUser(userInfo);
            if (1 == states) {
                model.addAttribute("redictUrl", "/login");
                return "registerSuccess";
            } else {
                model.addAttribute("error", true);
                model.addAttribute("errorInfo", "用户已存在");
                model.addAttribute("username", userName);
                return "register";
            }
        }
    }
}
