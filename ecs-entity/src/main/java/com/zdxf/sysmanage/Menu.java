package com.zdxf.sysmanage;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zdxf.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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
@TableName("sys_menu")
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
    private Integer pid;

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

    /**
     * 添加人
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updateUser;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 是否已删除：0正常 1已删除
     */
    @TableLogic(value="0",delval="1")
    private Integer deleted;


}
