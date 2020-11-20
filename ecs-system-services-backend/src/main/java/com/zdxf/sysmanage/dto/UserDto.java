package com.zdxf.sysmanage.dto;

import lombok.Data;

/**
 * @author Admin
 */
@Data
public class UserDto {
    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 规则
     */
    private String mobile;

    /**
     *
     */
    private String pwd;
    /**
     *
     */
    private String newPwd;
}
