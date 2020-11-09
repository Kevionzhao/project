package com.zdxf.sysmanage;

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
 * @author 鲲鹏
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user_role")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 人员ID
     */
    private Integer userId;

    /**
     * 角色ID
     */
    private String roleId;


}
