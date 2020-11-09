package com.zdxf.sysmanage.service;

import com.zdxf.common.base.IBaseService;
import com.zdxf.common.module.ResultJson;
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
     * 设置人员权限
     *
     * @param adminRulesDto 请求参数
     * @return
     */
    ResultJson setRules(UserRulesDto adminRulesDto);

    /**
     * 重置密码
     *
     * @param entity 实体对象
     * @return
     */
    ResultJson resetPwd(User entity);

}
