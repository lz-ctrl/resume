package com.resume.api.service;

import com.resume.api.dao.UserMapper;
import com.resume.api.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author lz
 */
@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User findUserByPhone(String phone){
        return null;
    }
}
