package com.example.ssoservernew.service;

import com.example.ssoservernew.dao.UserInfo;

public interface UserService {

    /**
     * 通过用户名，查询用户密码
     * @param userName
     * @return
     */
    public UserInfo findUserByName(String userName);

    /**
     * 注册用户时候的用户信息插入接口
     * @param userInfo
     * @return
     */
    public int insertUser(UserInfo userInfo);
}
