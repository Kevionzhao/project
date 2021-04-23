package com.zhao.bank1.service;

import com.zhao.bank1.entity.AccountPay;

/**
 * @author Admin
 */
public interface PayService {
    public AccountPay insertAccountPay(AccountPay accountPay);
    public AccountPay getAccountPay(String txId);
}
