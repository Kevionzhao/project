package com.zdxf.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zdxf.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Admin
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);

    User findUserByPassword(String password);

    int insertUser(User user);

}
