package com.zhao.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Admin
 */
public class JWTToken implements AuthenticationToken {

    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    /**
     * Returns the account identity submitted during the authentication process.
     */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     * Returns the credentials submitted by the user during the authentication process that verifies
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
