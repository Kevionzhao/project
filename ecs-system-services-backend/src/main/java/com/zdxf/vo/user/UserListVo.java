package com.zdxf.vo.user;

import lombok.Data;

/**
 * 人员列表Vo
 * @author Admin
 */
@Data
public class UserListVo {

    /**
     * 人员ID
     */
    private Integer id;
    /**
     * 手机号码
     */
    private String mobile;
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
    private String username;

    /**
     * 状态：1正常 2禁用
     */
    private Integer status;

    /**
     * 用户角色ID(多个规则逗号“,”隔开)
     */
    private String roleIds;
    /**
     * 规则列表
     */
    private String[] rulesList;

}
