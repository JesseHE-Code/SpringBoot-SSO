package com.example.ssoservernew.service;

import com.example.ssoservernew.dao.UserInfo;

public interface UserServiceInterface {

    /**
     * 通过用户名，查询用户密码
     * @param userName
     * @return
     */
    public UserInfo findUserByName(String userName);
}
