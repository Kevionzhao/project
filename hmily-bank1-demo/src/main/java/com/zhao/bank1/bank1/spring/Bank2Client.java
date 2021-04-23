package com.zhao.bank1.bank1.spring;

import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator.
 * @author Admin
 */
@FeignClient(value="hmily-bank2-demo",fallback= Bank2ClientFallback.class)
public interface Bank2Client {
    //远程调用李四的微服务
    @GetMapping("/transfer")
    //传递被调用者微服务信息到调用者
    @Hmily
    public  Boolean transfer(@RequestParam("amount") Double amount);
}
