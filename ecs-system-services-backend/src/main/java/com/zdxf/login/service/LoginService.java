package com.zdxf.login.service;

import com.zdxf.common.module.ResultJson;
import com.zdxf.login.dto.LoginDto;
import com.zdxf.utils.ResponseUserToken;

/**
 * @author Admin
 */
public interface LoginService {

    /**
     * 登录
     * @param loginDto
     * @return
     */
    ResponseUserToken login(LoginDto loginDto);

    /**
     * 获取菜单列表
     * @return
     */
    ResultJson getMenuList();

    /**
     * 退出登录
     *
     * @return
     */
    ResultJson logout();
}
