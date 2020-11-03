package com.zdxf.sysmanage.service;

import com.zdxf.sysmanage.LoginLog;

/**
 * <p>
 * 登录日志表 服务类
 * </p>
 *
 * @author Admin
 */
public interface ILoginLogService {
    /**
     * 创建系统登录日志
     *
     * @param loginLog 访问日志对象
     */
    void insertLoginLog(LoginLog loginLog);
}
