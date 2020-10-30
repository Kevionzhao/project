package com.zdxf.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Admin
 */
@EnableSwagger2
@SpringBootApplication(scanBasePackages = "com.zdxf.*")
@MapperScan("com.zdxf.system.mapper")
public class SystemAppServer {
    public static void main(String[] args) {
        SpringApplication.run(SystemAppServer.class,args);
    }
}
