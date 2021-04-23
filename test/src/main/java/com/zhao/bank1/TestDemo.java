package com.zhao.bank1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Admin
 */
@SpringBootApplication
@EnableEurekaClient
@RestController
public class TestDemo {
    @RequestMapping("/hello")
    public String getHelloMessage(){
        return "hello";
    }
    public static void main(String[] args) {
        SpringApplication.run(TestDemo.class, args);
    }
}
