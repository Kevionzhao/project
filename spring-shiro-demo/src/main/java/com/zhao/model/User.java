package com.zhao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Admin
 */
@Data
@Builder
@TableName("user_test")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String userName;
    private String passWord;
    private String role;
    private String permission;
    private Integer ban;
    private String salt;
    private Date createTime;
}
