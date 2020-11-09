package com.zdxf.login.controller;

import com.zdxf.common.module.ResultJson;
import com.zdxf.login.dto.LoginDto;
import com.zdxf.login.service.LoginService;
import com.zdxf.utils.ResponseUserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Admin
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService userService;

    @RequestMapping("/")
    public ResultJson<ResponseUserToken> login(@RequestBody LoginDto loginDto) {

        final ResponseUserToken userToken = userService.login(loginDto.getUserName(), loginDto.getPassWord());
        return ResultJson.success(userToken);
    }
}
