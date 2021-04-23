package com.zhao.bank1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Admin
 */
@SpringBootApplication
@EnableCaching
@MapperScan("com.zhao.bank1.mapper")
public class SpringBootTest {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootTest.class, args);
    }
}
