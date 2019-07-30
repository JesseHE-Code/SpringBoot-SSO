package com.example.ssoservernew.service;

import com.example.ssoservernew.dao.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
