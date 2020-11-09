package com.zdxf.login.service.impl;

import com.zdxf.common.enums.CodeType;
import com.zdxf.common.exception.CustomException;
import com.zdxf.common.module.ResultJson;
import com.zdxf.login.service.LoginService;
import com.zdxf.sysmanage.UserDetail;
import com.zdxf.sysmanage.mapper.UserMapper;
import com.zdxf.login.mapper.UserRolesMapper;
import com.zdxf.utils.JwtUtils;
import com.zdxf.utils.ResponseUserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Admin
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    UserMapper userMapper;
    @Resource
    private UserRolesMapper userRolesMapper;
    @Resource
    private JwtUtils jwtUtils;
    @Autowired
    private JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseUserToken login(String username, String password) {
        //用户验证
        final Authentication authentication = authenticate(username, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token ，查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        final UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        //通过工具类生成token
        final String token = "Bearer "+jwtUtils.generateAccessToken(userDetail);

        //存储token
        jwtUtils.putToken(username, token);
        // 学习 测试用,把用户的信息也返回了
        return new ResponseUserToken(token, userDetail);

    }


    private Authentication authenticate(String username, String password) {
        try {
            //该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，如果正确，则存储该用户名密码到“security 的 context中”
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
            throw new CustomException(ResultJson.error(CodeType.LOGIN_ERROR.getCode(), e.getMessage()));
        }
    }
}
