package com.zdxf.sysmanage;

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
 * 登录日志表
 * </p>
 *
 * @author Admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_login_log")
public class LoginLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /**
     * 登录IP地址
     */
    private String loginIp;

    /**
     * 登录地区
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录状态：0成功 1失败
     */
    private Integer status;

    /**
     * 类型：1登录系统 2退出系统
     */
    private Integer type;

    /**
     * 提示消息
     */
    private String msg;

}
