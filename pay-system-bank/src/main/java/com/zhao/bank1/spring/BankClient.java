package com.zhao.bank1.spring;

import com.zhao.bank1.entity.AccountPay;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Administrator.
 * @author Admin
 */
@FeignClient(value="rocket-bank1-demo",fallback = BankClientFallback.class)
public interface BankClient {
    /**
     * 远程调用支付系统
     * @param txNo
     * @return
     */
    @GetMapping("/pay/payresult/{txNo}")
    public AccountPay transfer(@PathVariable("txNo") String txNo);
}
