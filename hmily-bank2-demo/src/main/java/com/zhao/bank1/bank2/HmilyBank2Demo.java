package com.zhao.bank1.bank2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Admin
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan({"com.zhao.bank1.bank2","org.dromara.hmily"})
public class HmilyBank2Demo {
    public static void main(String[] args) {
        SpringApplication.run(HmilyBank2Demo.class, args);
    }
}
