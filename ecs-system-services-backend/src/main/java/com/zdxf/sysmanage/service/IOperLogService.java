package com.zdxf.sysmanage.service;

import com.zdxf.sysmanage.OperLog;
/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 * @author Admin
 */
public interface IOperLogService {
    /**
     * 创建系统操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(OperLog operLog);
}
