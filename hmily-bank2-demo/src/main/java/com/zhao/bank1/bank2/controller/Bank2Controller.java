package com.zhao.bank1.bank2.controller;

import com.zhao.bank1.bank2.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Admin
 */
@RestController
public class Bank2Controller {

    @Autowired
    AccountInfoService accountInfoService;

    @RequestMapping("/transfer")
    public Boolean bank2(@RequestParam("amount") Double amount){
        accountInfoService.updateAccountBalance("2", amount);
        return true;
    }
}
