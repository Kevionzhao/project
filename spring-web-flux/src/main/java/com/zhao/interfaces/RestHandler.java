package com.zhao.interfaces;

import com.zhao.bean.MethodInfo;
import com.zhao.bean.ServerInfo;

/**
 * @author Admin
 */
public interface RestHandler {
    /**
     * 初始花参数
     * @param serverInfo
     */
    void init(ServerInfo serverInfo);

    /**
     * 调用请求
     * @param methodInfo
     * @return
     */
    Object invokeRest(MethodInfo methodInfo);
}
