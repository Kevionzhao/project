package com.zdxf.sysmanage;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zdxf.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @author Admin
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别:1男 2女 3保密
     */
    private Integer gender;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱地址
     */
    private String email;

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
     * 部门ID
     */
    private Integer deptId;

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
     *  用户独立角色ID(多个规则逗号“,”隔开)
     */
    private String rules;

    /**
     * 用户角色ID(多个规则逗号“,”隔开)
     */
    private String roleIds;

    /**
     * 职务
     */
    private String position;

    /**
     * 登录次数
     */
    private Integer loginNum;

    /**
     * 最近登录IP
     */
    private String loginIp;

    /**
     * 最近登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

}