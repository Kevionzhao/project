package com.zdxf.sysmanage.service;

import com.zdxf.common.base.IBaseService;
import com.zdxf.common.module.ResultJson;
import com.zdxf.sysmanage.dto.UserDto;
import com.zdxf.sysmanage.dto.UserRulesDto;
import com.zdxf.sysmanage.User;

/**
 * <p>
 * 后台用户管理表 服务类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-03-26
 */
public interface UserService extends IBaseService<User> {

    /**
     * 重置密码
     *
     * @param userDto 实体对象
     * @return
     */
    ResultJson resetPwd(UserDto userDto);

}
