package com.zdxf.system.service.impl;

import com.zdxf.system.entity.Role;
import com.zdxf.system.entity.User;
import com.zdxf.system.entity.UserDetail;
import com.zdxf.system.mapper.RoleMapper;
import com.zdxf.system.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        List<Role> roleByUserId = roleMapper.findRoleByUserId(user.getId());

        return new UserDetail(user.getUsername(),roleByUserId,user.getPassword());

    }


}