package com.zdxf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Admin
 */
@EnableSwagger2
@SpringBootApplication(scanBasePackages = "com.zdxf.*")
@MapperScan("com.zdxf.*.mapper")
@EnableTransactionManagement
@EnableScheduling
public class SystemAppServer {
    public static void main(String[] args) {
        SpringApplication.run(SystemAppServer.class,args);
    }
}
