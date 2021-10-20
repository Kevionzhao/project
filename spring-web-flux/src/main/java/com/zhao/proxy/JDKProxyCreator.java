package com.zhao.proxy;

import com.zhao.annotation.Api;
import com.zhao.bean.MethodInfo;
import com.zhao.bean.ServerInfo;
import com.zhao.interfaces.ProxyCreator;
import com.zhao.interfaces.RestHandler;
import com.zhao.interfaces.WebClientRestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Admin
 */
@Slf4j
public class JDKProxyCreator implements ProxyCreator {
    /**
     * create proxy class
     *
     * @param clazz
     * @return
     */
    @Override
    public Object createProxy(Class<?> clazz) {
        log.info("create proxy " + clazz);
        ServerInfo serverInfo = getServerInfo(clazz);
        log.info("serverInfo" + serverInfo);
        RestHandler handler = new WebClientRestHandler();
        handler.init(serverInfo);
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                MethodInfo methodInfo = getMethodInfo(method,args);
                log.info("methodInfo" + methodInfo);
                return handler.invokeRest(methodInfo);
            }

            private MethodInfo getMethodInfo(Method method, Object[] args) {
                MethodInfo methodInfo = new MethodInfo();

                extractedUrlAndMethod(method, methodInfo);
                extractedRequestParamAndBody(method, args, methodInfo);
                extractedReturnInfo(method, methodInfo);
                return methodInfo;
            }
        });
    }

    /**
     * 提取返回对象信息
     * @param method
     * @param methodInfo
     */
    private void extractedReturnInfo(Method method, MethodInfo methodInfo) {
        boolean isFlux = method.getReturnType().isAssignableFrom(Flux.class);
        methodInfo.setReturnFlux(isFlux);
        Class<?> elementType = extractedElementType(method.getGenericReturnType());
        methodInfo.setReturnElementType(elementType);
    }

    private Class<?> extractedElementType(Type genericReturnType){
        Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }

    /**
     * 提取请求参数
     * @param method
     * @param args
     * @param methodInfo
     */
    private void extractedRequestParamAndBody(Method method, Object[] args, MethodInfo methodInfo) {
        Parameter[] methodParameters = method.getParameters();
        Map<String,Object> paramMap = new LinkedHashMap<>();
        methodInfo.setParams(paramMap);
        for (int i = 0; i < methodParameters.length; i++) {
            PathVariable pathVariable = methodParameters[i].getAnnotation(PathVariable.class);
            if (pathVariable!=null){
                paramMap.put(pathVariable.value(), args[i]);
            }
            RequestBody requestBody = methodParameters[i].getAnnotation(RequestBody.class);
            if (requestBody!=null){
                methodInfo.setBody((Mono<?>) requestBody);
            }
        }
    }

    /**
     * 提取请求方法和URL
     * @param method
     * @param methodInfo
     */
    private void extractedUrlAndMethod(Method method, MethodInfo methodInfo) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations){
            if (annotation instanceof GetMapping){
                methodInfo.setUrl(((GetMapping) annotation).value()[0]);
                methodInfo.setMethod(HttpMethod.GET);
            }
            else if (annotation instanceof PostMapping){
                methodInfo.setUrl(((GetMapping) annotation).value()[0]);
                methodInfo.setMethod(HttpMethod.POST);
            }
            else if (annotation instanceof DeleteMapping){
                methodInfo.setUrl(((GetMapping) annotation).value()[0]);
                methodInfo.setMethod(HttpMethod.DELETE);
            }

        }
    }

    private ServerInfo getServerInfo(Class<?> clazz) {
        ServerInfo serverInfo = new ServerInfo();
        Api annotation = clazz.getAnnotation(Api.class);
        serverInfo.setServerAddress(annotation.value());
        return serverInfo;
    }
}
