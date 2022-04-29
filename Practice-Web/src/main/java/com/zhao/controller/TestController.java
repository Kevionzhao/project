package com.zhao.controller;

import com.zhao.annotiation.AccessLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Admin
 */
@RestController
public class TestController {

    @GetMapping("/index")
    @AccessLimit(seconds = 1, maxCount = 3) //1秒内 允许请求3次
    public String getImageList(){
        return "hello";
    }
}
