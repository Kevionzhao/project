package com.zdxf.system.controllerTest;

import com.zdxf.common.annotation.Log;
import com.zdxf.common.enums.BusinessType;
import com.zdxf.common.exception.CustomException;
import com.zdxf.common.module.Result;
import com.zdxf.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

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

    @GetMapping("/test/{id}")
    @ApiOperation("测试请求")
    @Log(title = "定时任务管理", businessType = BusinessType.CLEAN)
    public Result<String> getTestResult(@PathVariable String id){
        System.out.println(id);
        if (!Objects.equals(id,"Jack")){
            throw new CustomException("参数不对");
        }
        return Result.succeed("test ok");
    }
//    /**
//     * 注册
//     * @param user
//     */
//    @PostMapping("/register")
//    public ResultJson signUp(@RequestBody User user , String str) {
//        if (user==null){
//            ResultJson.failure(ResultCode.BAD_REQUEST);
//        }
//        userService.register(user,str);
//
//        return ResultJson.success();
//
//    }
//
//    /**
//     * 获取token
//     * @param user
//     * @return
//     */
//    @PostMapping("/login")
//    public ResultJson<ResponseUserToken> login(@RequestBody User user) {
//
//        final ResponseUserToken response = userService.login(user.getUsername(), user.getPassword());
//        return ResultJson.ok(response);
//    }
}
