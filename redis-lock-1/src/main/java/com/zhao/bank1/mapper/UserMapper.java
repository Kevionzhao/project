package com.zhao.bank1.mapper;

import com.zhao.bank1.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Admin
 */
@Mapper
public interface UserMapper {
    @Select("select * from User where id = #{id}")
    public User getUserById(Integer id);
}
