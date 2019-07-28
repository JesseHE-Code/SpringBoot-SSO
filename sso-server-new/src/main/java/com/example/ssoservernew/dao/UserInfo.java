package com.example.ssoservernew.dao;

/**
 * 用户信息类
 * 包含：用户名，用户密码和用户角色
 * 每一个属性包含一个get和set方法
 */
public class UserInfo {

    private int userId;
    private String userName;
    private String password;
    private String role;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
