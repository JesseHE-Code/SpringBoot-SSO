package com.example.ssoservernew.dao;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户信息类
 * 包含：用户名，用户密码和用户角色
 * 每一个属性包含一个get和set方法
 */
@Getter
@Setter
public class UserInfo {

    private int userId;
    private String userName;
    private String password;
    private String role;
}
