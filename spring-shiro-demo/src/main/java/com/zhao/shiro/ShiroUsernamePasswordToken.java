package com.zhao.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author Admin
 */
public class ShiroUsernamePasswordToken extends UsernamePasswordToken {
    /*
     * 当前登录用户类型
     */
    private String userType;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public ShiroUsernamePasswordToken(String username, String password, String userType) {
        super(username, password);
        this.userType = userType;
    }
}
