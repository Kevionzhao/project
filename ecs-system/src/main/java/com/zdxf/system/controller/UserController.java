package com.zdxf.system.controller;

import com.zdxf.common.annotation.Log;
import com.zdxf.common.enums.BusinessType;
import com.zdxf.system.entity.User;
import com.zdxf.system.result.ResultCode;
import com.zdxf.system.result.ResultJson;
import com.zdxf.system.service.UserService;
import com.zdxf.system.utils.ResponseUserToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author Admin
 */
@Slf4j
@Api(tags = "测试案例")
@RestController
@RequestMapping("/userController")
public class UserController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    UserService userService;

    @GetMapping("/test")
    @ApiOperation("测试请求")
    @Log(title = "定时任务管理", businessType = BusinessType.CLEAN)
    public String getTestResult(){
        log.info("test method info");
        log.debug("test method debug");
        log.error("test method error");
        return "success";
    }
    /**
     * 注册
     * @param user
     */
    @PostMapping("/register")
    public ResultJson signUp(@RequestBody User user , String str) {
        if (user==null){
            ResultJson.failure(ResultCode.BAD_REQUEST);
        }
        userService.register(user,str);

        return ResultJson.success();

    }

    /**
     * 获取token
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResultJson<ResponseUserToken> login(@RequestBody User user) {

        final ResponseUserToken response = userService.login(user.getUsername(), user.getPassword());
        return ResultJson.ok(response);
    }
}
