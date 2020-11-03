package com.zdxf.sysmanage.controller;

import com.zdxf.common.annotation.Log;
import com.zdxf.common.base.BaseController;
import com.zdxf.common.enums.BusinessType;
import com.zdxf.common.module.ResultJson;
import com.zdxf.sysmanage.service.IUserService;
import com.zdxf.sysmanage.User;
import com.zdxf.sysmanage.query.UserQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Admin
 */
@Slf4j
@Api(tags = "人员管理")
@RestController
@RequestMapping("/userController")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 用户信息列表查询
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("人员管理")
    @Log(title = "人员管理",businessType = BusinessType.OTHER)
    public ResultJson list(@RequestBody UserQuery query){
           return userService.getList(query);
    }

    /**
     * 添加人员
     *
     * @param entity 实体对象
     * @return
     */
    @Log(title = "人员管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public ResultJson add(@RequestBody User entity) {
        return userService.edit(entity);
    }
}
