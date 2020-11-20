package com.zdxf.sysmanage;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zdxf.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author Admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_menu")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * URL地址
     */
    private String url;

    /**
     * 参数
     */
    private String param;

    /**
     * 父级ID
     */
    private Integer pId;

    /**
     * 类型：1目录 2菜单 3节点
     */
    private Integer type;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 是否显示：1显示 2不显示
     */
    private Integer status;

    /**
     * 是否公共：1是 2否
     */
    private Integer isPublic;

    /**
     * 备注
     */
    private String note;

    /**
     * 显示顺序
     */
    private Integer sort;

}
