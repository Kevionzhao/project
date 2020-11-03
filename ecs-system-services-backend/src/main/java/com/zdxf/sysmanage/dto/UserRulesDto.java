package com.zdxf.sysmanage.dto;

import lombok.Data;

/**
 * 人员权限Dto
 * @author Admin
 */
@Data
public class UserRulesDto {

    /**
     * 角色ID
     */
    private Integer id;

    /**
     * 规则
     */
    private String rules;

}
