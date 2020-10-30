package com.zdxf.system.service;

import com.zdxf.system.entity.User;
import com.zdxf.system.utils.ResponseUserToken;

public interface UserService {

    /**
     * 注册用户
     * @return
     */
    void register(User user, String str);


    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    ResponseUserToken login(String username, String password);
}
