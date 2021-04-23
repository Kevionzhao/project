package com.zhao.bank1.spring;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Admin
 */
@FeignClient(value="bank2-demo")
public interface Bank2Client {
    //远程调用李四的微服务
    @GetMapping("/transfer")
    public  String transfer(@RequestParam("amount") Double amount);
}
