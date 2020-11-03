package com.zdxf.sysmanage.service;

import com.zdxf.sysmanage.User;
import com.zdxf.sysmanage.utils.ResponseUserToken;

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
