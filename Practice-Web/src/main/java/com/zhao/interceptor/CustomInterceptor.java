package com.zhao.interceptor;

import com.zhao.annotiation.AccessLimit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Admin
 */
@Component
public class CustomInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String ip = request.getRemoteAddr();
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit methodAnnotation = ((HandlerMethod) handler).getMethodAnnotation(AccessLimit.class);
            if (null == methodAnnotation) {
                return true;
            }
            int seconds = methodAnnotation.seconds();
            int maxCount = methodAnnotation.maxCount();
            boolean login = methodAnnotation.needLogin();
            if (login) {
                System.out.println("please login first");
            }
            String servletPath = request.getServletPath();
            String key = "servletPath";
            Integer count = (Integer) redisTemplate.opsForValue().get(key);
            if (null == count || -1 == count) {
//                redisTemplate.opsForValue().set(key, 1, seconds, TimeUnit.SECONDS);
                redisTemplate.opsForValue().set(key,1);
                return true;
            }

            if (count < maxCount) {
                count = count + 1;
                redisTemplate.opsForValue().set(key, count, 0);
                return true;
            }
            throw new Exception("操作过于频繁");
        }
        return true;
    }
}
