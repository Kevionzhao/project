package com.zdxf.sysmanage;

import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * 操作日志表
 * @author Admin
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@TableName("sys_oper_log")
public class OperLog extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 模块标题
     */
    private String title;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    private Integer businessType;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    private Integer operatorType;

    /**
     * 操作人员
     */
    private String operName;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 请求URL
     */
    private String operUrl;

    /**
     * 主机地址
     */
    private String operIp;

    /**
     * 操作地点
     */
    private String operLocation;

    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 返回参数
     */
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

//    /**
//     * 操作时间
//     */
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date createTime;
//
//    /**
//     * 是否已删除：0正常 1已删除
//     */
//    @TableLogic(value="0",delval="1")
//    private Integer deleted;
}
