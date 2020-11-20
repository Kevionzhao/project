package com.zdxf.sysmanage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zdxf.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色菜单关联表
 * </p>
 *
 * @author Admin
 */
@Data
@EqualsAndHashCode()
@Accessors(chain = true)
@TableName("t_role_menu")
public class RoleMenu {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * type对应的值
     */
    private Integer roleId;

    /**
     * 菜单ID
     */
    private Integer menuId;


}
