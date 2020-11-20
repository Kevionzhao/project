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
 * 人员角色表
 * </p>
 *
 * @author Admin
 */
@Data
@EqualsAndHashCode()
@Accessors(chain = true)
@TableName("t_user_role")
public class UserRole {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 人员ID
     */
    private Integer userId;

    /**
     * 角色ID
     */
    private String roleId;


}
