package com.zhao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Admin
 */
@Data
@TableName("role_test")
public class Role {
    private Integer id;
    private String permission;;
    private String role;
}
