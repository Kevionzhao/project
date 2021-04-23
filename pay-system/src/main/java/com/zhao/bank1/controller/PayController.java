package com.zhao.bank1.controller;

import com.zhao.bank1.service.PayService;
import com.zhao.bank1.entity.AccountPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Admin
 */
@RestController
@RequestMapping
public class PayController {
    @Autowired
    PayService payService;
    //充值
    @GetMapping(value = "/paydo")
    public AccountPay pay(AccountPay accountPay){
        //事务号
        String txNo = UUID.randomUUID().toString();
        accountPay.setId(txNo);
        return payService.insertAccountPay(accountPay);
    }
    //查询充值结果
    @GetMapping(value = "/payresult/{txNo}")
    public AccountPay payresult(@PathVariable("txNo") String txNo){
        return payService.getAccountPay(txNo);
    }
}
