package com.zdxf.login.controller;

import com.zdxf.common.module.ResultJson;
import com.zdxf.login.dto.LoginDto;
import com.zdxf.login.service.LoginService;
import com.zdxf.utils.ResponseUserToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Admin
 */
@RestController
@RequestMapping("/login")
@Api("用户登录模块")
@Slf4j
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    @ApiOperation(value="登录模块---登录校验")
    public ResultJson<ResponseUserToken> login(@RequestBody LoginDto loginDto) {

        return  ResultJson.success(loginService.login(loginDto));
    }

    /**
     * 获取菜单列表
     *
     * @return
     */
    @GetMapping("/getMenuList")
    public ResultJson getMenuList() {
        return loginService.getMenuList();
    }
    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("/logout")
    public ResultJson logout() {
        // 退出
        return loginService.logout();
    }
}
