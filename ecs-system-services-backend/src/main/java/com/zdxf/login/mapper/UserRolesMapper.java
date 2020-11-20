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
     * 根据用户登录名获取角色
     * @param userName
     * @return
     */
    String findRolesByUserId(String userName);

    /**
     * 判断改用户和角色是否有绑定关系
     * @param userRole
     * @return
     */
    int selectCountByCondition(UserRole userRole);
}
