package com.zhao.bank1.bank1.controller;

import com.zhao.bank1.bank1.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Admin
 */
@RestController
public class Bank1Controller {

    @Autowired
    AccountInfoService accountInfoService;

    @RequestMapping("/transfer")
    public void bank1(@RequestParam("accountNo") String accountNo,@RequestParam("amount") Double amount){
        accountInfoService.updateAccountBalance(accountNo, amount);
    }
}
