package com.zhao.controller;

import com.zhao.mapper.UserMapper;
import com.zhao.model.Msg;
import com.zhao.model.User;
import com.zhao.service.UserService;
import com.zhao.util.JWTUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

/**
 * @author Admin
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value = "用户登录", notes = "登录--不进行拦截")
    @PostMapping("/login")
    public Msg login(@RequestParam("username") String username,
                     @RequestParam("password") String password) {
        String type = "Admin";
        String realPassword = userService.getPassword(username);
        User user = userService.getUser(username);
        String md5PassWord = new SimpleHash("MD5", password, user.getSalt(), 1).toHex();
        if (realPassword == null) {
            return Msg.fail().add("info","用户名错误");
        } else if (!realPassword.equals(md5PassWord)) {
            return Msg.fail().add("info","密码错误");
        } else {
            return Msg.success().add("token", JWTUtil.createToken(username,password,type));
        }
    }


    @ApiOperation(value = "无权限", notes = "无权限跳转的接口")
    @RequestMapping(path = "/unauthorized/{message}")
    public Msg unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return Msg.fail().add("info",message);
    }

    @ApiOperation(value = "特定用户访问", notes = "拥有 user, admin 角色的用户可以访问下面的页面")
    @PostMapping("/getMessage")
    @RequiresRoles(logical = Logical.OR, value = {"vis"})
    public Msg getMessage() {
        return Msg.success().add("info","成功获得信息！");
    }

    @ApiOperation(value = "Vip用户访问", notes = "拥有 vip 权限可以访问该页面")
    @PostMapping("/getVipMessage")
//    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    @RequiresPermissions("system:menu:add")
    public Msg getVipMessage() {
        return Msg.success().add("info","成功获得 vip 信息！");
    }

    @RequestMapping("/insertUser")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public int insertUser(@RequestBody User user){
        //将uuid设置为密码盐值
        String salt = UUID.randomUUID().toString().replaceAll("-","");
        SimpleHash simpleHash = new SimpleHash("MD5", user.getPassWord(), salt, 1);
        user.setPassWord(simpleHash.toHex());
        user.setBan(0);
        user.setSalt(salt);
        user.setCreateTime(new Date());
        return  userMapper.insert(user);
    }

}
