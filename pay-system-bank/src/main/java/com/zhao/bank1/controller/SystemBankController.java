package com.zhao.bank1.controller;

import com.zhao.bank1.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Admin
 */
@RestController
public class SystemBankController {

    @Autowired
    PayService payService;

    @GetMapping("/getMsg/{txId}")
    public void updateBankAccount(@PathVariable("txId") String txid){
        payService.getMsgFromPaySystem(txid);
    }
}
