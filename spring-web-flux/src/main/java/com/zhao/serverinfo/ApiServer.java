package com.zhao.serverinfo;

import com.zhao.annotation.Api;
import com.zhao.bean.User;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Admin
 */
@Api("http://localhost:8700/helloWebFlux")
public interface ApiServer {
    /**
     * 获取所有用户
     * @return
     */
//    @GetMapping("/")
//    Flux<User> getAllUser();

    @GetMapping("/")
    Mono<String> test();
}
