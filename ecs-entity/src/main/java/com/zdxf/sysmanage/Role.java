package com.zdxf.sysmanage;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zdxf.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 角色信息表
 * @author Admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @NotNull(message = "角色名称不能为空")
    private String name;

    /**
     * 角色拥有的菜单ID，多个规则","隔开
     */
    private String menuIds;

    /**
     * 状态：1正常 2禁用
     */
//    @NotNull(message = "状态不能为空")
    private Integer status;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer sort;

}

