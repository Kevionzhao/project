package com.zhao.bank1.service;

import com.zhao.bank1.entity.User;
import com.zhao.bank1.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Admin
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;
    @Override
    public User getUser(Integer id) {
        User user = userMapper.getUserById(id);
        return user;
    }
}
