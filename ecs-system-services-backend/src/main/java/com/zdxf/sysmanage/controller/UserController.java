package com.zdxf.sysmanage.controller;

import com.zdxf.common.annotation.Log;
import com.zdxf.common.base.BaseController;
import com.zdxf.common.enums.BusinessType;
import com.zdxf.common.module.ResultJson;
import com.zdxf.sysmanage.dto.UserDto;
import com.zdxf.sysmanage.service.UserService;
import com.zdxf.sysmanage.User;
import com.zdxf.sysmanage.query.UserQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author Admin
 */
@Slf4j
@Api(tags = "系统管理----用户管理")
@RestController
@RequestMapping("/userController")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 用户信息列表查询
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("系统管理----获取用户信息列表")
    public ResultJson list(@RequestBody UserQuery query){
           return userService.getList(query);
    }

    /**
     * 添加人员
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation("系统管理----新增用户")
    public ResultJson add(@RequestBody User entity) {
        return userService.edit(entity);
    }

    /**
     * 获取用户详情信息
     * @param userDto
     * @return
     */
    @PostMapping("/getInfo")
    @ApiOperation("系统管理----查询用户详情")
    public ResultJson<User> getInfo(@RequestBody UserDto userDto){
        Integer id = userDto.getId();
        if (Objects.isNull(userDto)&&Objects.isNull(userDto.getId())){
            return ResultJson.error("请求参数错误，id不能为空");
        }else {
            Object user = userService.getInfo(id);
            return ResultJson.success("success", user);
        }
    }

    /**
     * 删除用户账号
     * @param userDto
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation("系统管理----删除用户信息")
    @Log(title = "用户管理",businessType = BusinessType.DELETE)
    public ResultJson delete(@RequestBody UserDto userDto){
        Integer id = userDto.getId();
        if (Objects.isNull(userDto)&&Objects.isNull(userDto.getId())){
            return ResultJson.error("请求参数错误，id不能为空");
        }else {
            return userService.deleteById(id);
        }
    }

    /**
     * 重置密码
     *
     * @param userDto 数据传输对象
     * @return
     */
    @PostMapping("/resetPwd")
    public ResultJson resetPwd(@RequestBody UserDto userDto) {
        return userService.resetPwd(userDto);
    }
}
