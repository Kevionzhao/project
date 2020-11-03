package com.zdxf.sysmanage.service.impl;

import com.zdxf.common.enums.CodeType;
import com.zdxf.common.exception.CustomException;
import com.zdxf.common.module.ResultJson;
import com.zdxf.sysmanage.mapper.UserRolesMapper;
import com.zdxf.sysmanage.service.UserService;
import com.zdxf.sysmanage.User;
import com.zdxf.sysmanage.UserDetail;
import com.zdxf.sysmanage.UserRole;
import com.zdxf.sysmanage.mapper.UserMapper;
import com.zdxf.sysmanage.utils.JwtUtils;
import com.zdxf.sysmanage.utils.ResponseUserToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Admin
 */
@Service
public class UserServiceImp implements UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    private UserRolesMapper userRolesMapper;
    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private AuthenticationManager authenticationManager;


    @Override
    public void register(User user,String str) {

        //查询用户
        User oldUser = userMapper.findUserByPassword(user.getPassword());

        if (oldUser != null) {

            throw new CustomException(ResultJson.error(CodeType.BAD_REQUEST.getCode(), "用户已存在"));
        }
        //加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
//        user.setUpdatetime(new Date(System.currentTimeMillis()));
        user.setUpdateTime(new Date());
        user.setStatus(1);
        userMapper.insertUser(user);

        if (StringUtils.isNotBlank(str)){
            //权限插入
            String[] roles = str.split(",");
            for (String role : roles) {
                //如果原先有绑定权限就删除
                // userRolesMapper.deleteById(user.getId());

                UserRole userRoles = new UserRole();
                userRoles.setUserId(user.getId());
                userRoles.setRoleId(Integer.parseInt(role));
                userRolesMapper.insert(userRoles);
            }
        }

    }

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
