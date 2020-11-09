package com.zdxf.login.service.impl;

import com.zdxf.sysmanage.Role;
import com.zdxf.sysmanage.User;
import com.zdxf.sysmanage.UserDetail;
import com.zdxf.sysmanage.mapper.UserMapper;
import com.zdxf.login.mapper.UserRolesMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.Arrays;
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
    private UserRolesMapper userRolesMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No userDetail found with username '%s'.", username));
        }
        //查询权限封装
        String roleIds = userRolesMapper.findRolesByUserId(user.getUserName());
        List<String> roleIdsList = Arrays.asList(roleIds.split(","));
        return new UserDetail(user.getUserName(),roleIdsList,
                //解决 SpringBoot Security：Encoded password does not look like BCrypt
                new BCryptPasswordEncoder().encode(user.getPassword()));

    }


}