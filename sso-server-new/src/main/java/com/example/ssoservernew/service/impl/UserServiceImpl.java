package com.example.ssoservernew.service.impl;

import com.example.ssoservernew.dao.UserInfo;
import com.example.ssoservernew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.expression.ExpressionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserInfo findUserByName(String userName) {
        try {
            UserInfo userInfo = jdbcTemplate.queryForObject("select * from userTable where user_name = \"" + userName + "\"", new RowMapper<UserInfo>() {
                @Override
                public UserInfo mapRow(ResultSet arg0, int arg1) throws SQLException {
                    UserInfo user = new UserInfo();
                    user.setUserName(arg0.getString("user_name"));
                    user.setPassword(arg0.getString("user_password"));
                    user.setRole(arg0.getString("user_role"));
                    return user;
                }
            });
            return userInfo;
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public int insertUser(UserInfo userInfo){
        int states = 0;
        //先判断用户名是否被注册
        List<String> password = jdbcTemplate.queryForList("select user_password from userTable where user_name = \"" + userInfo.getUserName() + "\"",String.class);
        if(password.isEmpty())
        {
            int temp = jdbcTemplate.update("insert into userTable(`user_name`, `user_password`, `user_role`) values (?,?,?)", userInfo.getUserName(), userInfo.getPassword(), userInfo.getRole());
            if(temp > 0){
                states = 1;
            }
            else
            {
                states = 2;
            }
        }
        else {
            states = 3;
        }

        return states;
    }
}
