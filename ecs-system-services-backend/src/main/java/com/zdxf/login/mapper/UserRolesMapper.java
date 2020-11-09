package com.zdxf.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zdxf.sysmanage.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Admin
 */
@Mapper
public interface UserRolesMapper extends BaseMapper<UserRole> {
    /**
     * 根据用户登录Id获取角色
      * @param id
     * @return
     */
    String findRolesByUserId(String userName);
}
