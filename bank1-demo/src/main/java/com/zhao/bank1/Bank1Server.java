package com.zhao.bank1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Admin
 */
@EnableEurekaClient
@RestController
@EnableFeignClients(basePackages = {"com.zhao.bank1.spring"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Bank1Server {
    @RequestMapping("/")
    public String home() {
        return "Hello World";
    }
    public static void main(String[] args) {
        SpringApplication.run(Bank1Server.class, args);
    }
}
