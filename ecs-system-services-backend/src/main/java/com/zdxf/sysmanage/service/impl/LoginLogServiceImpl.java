package com.zdxf.sysmanage.service.impl;

import com.zdxf.sysmanage.service.LoginLogService;
import com.zdxf.sysmanage.LoginLog;
import org.springframework.stereotype.Service;

/**
 * 处理登录日志
 * @author Admin
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {
    /**
     * 创建系统登录日志
     *
     * @param loginLog 访问日志对象
     */
    @Override
    public void insertLoginLog(LoginLog loginLog) {

    }
}
