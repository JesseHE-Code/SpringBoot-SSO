package com.example.ssoservernew.dao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @Author :hezhiqiang06
 * @Date :2019-09-01 21:57
 **/

@Getter
@Setter
public class UserMapFace {
    Integer id;
    String userName;
    String faceId;
}
