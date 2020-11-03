package com.zdxf.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 人员表单Vo
 */
@Data
public class UserInfoVo {

    /**
     * 人员ID
     */
    private Integer id;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别:1男 2女 3保密
     */
    private Integer gender;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 公司ID
     */
    private Integer companyId;

    /**
     * 职务
     */
    private String position;

    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 状态：1正常 2禁用
     */
    private Integer status;

    /**
     * 人员独立权限的菜单ID，多个规则逗号“,”隔开
     */
    private String rules;

    /**
     * 用户角色ID(多个规则逗号“,”隔开)
     */
    private String roleIds;

}
