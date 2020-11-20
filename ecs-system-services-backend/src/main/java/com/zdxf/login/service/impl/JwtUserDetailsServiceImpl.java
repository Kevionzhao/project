package com.zdxf.login.service.impl;

import com.zdxf.sysmanage.Role;
import com.zdxf.sysmanage.User;
import com.zdxf.sysmanage.UserDetail;
import com.zdxf.sysmanage.mapper.RoleMapper;
import com.zdxf.sysmanage.mapper.UserMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Admin
 */
@Configuration
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No userDetail found with username '%s'.", username));
        }
        //查询权限封装
        List<Role> roleByUserId = roleMapper.findRoleByUserId(user.getId());
        return new UserDetail(user.getUserName(),roleByUserId,
                //解决 SpringBoot Security：Encoded password does not look like BCrypt
                //数据库的密码没加密，进行加密；应用与数据库密码是明文的时候
                //passwordEncoder.encode(user.getPassword())
                // SpringSecurity 登录权限验证会对登录的明文密码加密后和数据库中的密码比对，如果数据密码已加密，springsecurity 应采用同样的加密方式加密
                user.getPassword()

        );
    }
}