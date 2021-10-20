package com.zhao.service;

import com.zhao.mapper.UserMapper;
import com.zhao.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Admin
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public String getPassword(String username){
        return  userMapper.getPassword(username);
    }

    public int checkUserBanStatus(String username){
        return userMapper.checkUserBanStatus(username);
    }

    public String getRole(String username){
        return userMapper.getRole(username);
    }

    public String getRolePermission(String username){
        return userMapper.getRolePermission(username);
    }

    public String getPermission(String username){
        return userMapper.getPermission(username);
    }

    public User getUser(String username){return userMapper.getUser(username);}


    public String getRolesList(String username){return userMapper.getRoleList(username);};

    public String getPermissionList(String username){return userMapper.getPermissionList(username);};

}
