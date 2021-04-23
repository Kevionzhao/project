package com.zhao.bank1.bank3.controller;

import com.zhao.bank1.bank3.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Admin
 */
@RestController
public class Bank2Controller {

    @Autowired
    AccountInfoService accountInfoService;

    /**
     * 转账接受地址
     * @param amount
     * @return
     */
    @GetMapping("/transfer")
    public String transfer(Double amount){
        //李四增加金额
        accountInfoService.updateAccountBalance("2",amount);
        return "bank2"+amount;
    }
}
