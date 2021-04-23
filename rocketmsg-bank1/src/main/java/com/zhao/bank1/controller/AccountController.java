package com.zhao.bank1.controller;

import com.zhao.bank1.model.AccountChangeEvent;
import com.zhao.bank1.service.Bank1Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Admin
 */
@RequestMapping
@RestController
@Slf4j
public class AccountController {

    @Autowired
    Bank1Service bank1Service;

    @GetMapping("/transfer")
    public String accountTransfer(@Param("accountNo") String accountNo,@Param("amount") Double amount){
        String txNo = UUID.randomUUID().toString();
        AccountChangeEvent accountChangeEvent = new AccountChangeEvent();
        accountChangeEvent.setTxNo(txNo);
        accountChangeEvent.setAccountNo(accountNo);
        accountChangeEvent.setAmount(amount);
        bank1Service.sendAccountUpdateMessage(accountChangeEvent);
        return "transfer successful";
    }
}
