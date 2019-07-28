package com.example.ssoservernew;

import com.example.ssoservernew.dao.UserInfo;
import com.example.ssoservernew.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SsoServerNewApplicationTests {

    @Autowired
    UserService userService;
    @Test
    public void findUser() {
        UserInfo userInfo = userService.findUserByName("hezhiqiang");
        System.out.println("UserName:" + userInfo.getUserName());
        System.out.println("UserPassword:" + userInfo.getPassword());
        System.out.println("UserRole:" + userInfo.getRole());
    }

    @Test
    public void findNoUser(){
        UserInfo userInfo = userService.findUserByName("hezhiqiang-1");
        if(userInfo == null){
            System.out.println("IS NULL!");
        }
    }

}
