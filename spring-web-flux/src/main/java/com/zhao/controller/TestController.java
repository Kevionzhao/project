package com.zhao.controller;

import com.zhao.serverinfo.ApiServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Admin
 */
@RestController
public class TestController {

    @Autowired
    ApiServer apiServer;

    @GetMapping("/")
    public void test(){
        Mono<String> test = apiServer.test();
        test.subscribe(System.out::println);
        System.out.println("successful++++++++++++++");
    }
}
