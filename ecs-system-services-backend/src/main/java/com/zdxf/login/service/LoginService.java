package com.zdxf.login.service;

import com.zdxf.utils.ResponseUserToken;

/**
 * @author Admin
 */
public interface LoginService {

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    ResponseUserToken login(String username, String password);
}
