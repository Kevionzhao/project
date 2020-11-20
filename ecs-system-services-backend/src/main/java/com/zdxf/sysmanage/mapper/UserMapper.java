package com.zdxf.sysmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zdxf.sysmanage.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Admin
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);
}
