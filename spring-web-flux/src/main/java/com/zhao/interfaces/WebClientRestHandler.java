package com.zhao.interfaces;

import com.zhao.bean.MethodInfo;
import com.zhao.bean.ServerInfo;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Admin
 */
public class WebClientRestHandler implements RestHandler{

    private WebClient client;
    /**
     * 初始花参数
     *
     * @param serverInfo
     */
    @Override
    public void init(ServerInfo serverInfo) {
        this.client = WebClient.create(serverInfo.getServerAddress());
    }

    /**
     * 调用请求
     *
     * @param methodInfo
     * @return
     */
    @Override
    public Object invokeRest(MethodInfo methodInfo) {
        Object result = null;
        WebClient.ResponseSpec retrieve = this.client.method(methodInfo.getMethod())
                .uri(methodInfo.getUrl())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
        if (methodInfo.isReturnFlux()){
            result = retrieve.bodyToFlux(methodInfo.getReturnElementType().getClass());
        }else {
            result = retrieve.bodyToMono(methodInfo.getReturnElementType().getClass());
        }
        return result;
    }
}
