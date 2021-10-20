package com.zhao.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author Admin
 */
@RestController
@Slf4j
public class SpringMvcController {

    @RequestMapping("/helloSpringMvc")
    public String helloSpringMvc() {
        log.info("【helloSpringMvc start】");
        doSomeThing();
        log.info("【helloSpringMvc end】");
        return "Hello SpringMVC";
    }

    private String doSomeThing() {

        try {
            //模拟耗时操作
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Hello WebFlux";
    }
}
