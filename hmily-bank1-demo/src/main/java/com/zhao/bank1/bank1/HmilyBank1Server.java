package com.zhao.bank1.bank1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Admin
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.zhao.bank1.bank1.spring" })
@ComponentScan({"com.zhao.bank1.bank1","org.dromara.hmily"})
@EnableAspectJAutoProxy
public class HmilyBank1Server {
    public static void main(String[] args) {
        SpringApplication.run(HmilyBank1Server.class, args);
    }
}
