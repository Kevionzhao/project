package com.zhao.bank1.spring;

import com.zhao.bank1.entity.AccountPay;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version 1.0
 **/
@Component
public class BankClientFallback implements BankClient {

    @Override
    public AccountPay transfer(String txNo) {
        AccountPay accountPay = new AccountPay();
        accountPay.setResult("fail");
        return accountPay;
    }
}
