package com.zhao.bank1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Admin
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.zhao.spring"})
public class BankServiceDemo {
    public static void main(String[] args) {
        SpringApplication.run(BankServiceDemo.class, args);
    }
}
