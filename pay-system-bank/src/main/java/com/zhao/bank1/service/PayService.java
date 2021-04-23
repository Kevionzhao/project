package com.zhao.bank1.service;

import com.zhao.bank1.entity.AccountPay;
import com.zhao.bank1.model.AccountChangeEvent;

/**
 * @author Admin
 */
public interface PayService {
        public void updateAccountBalance(AccountChangeEvent accountChangeEvent);
        public AccountPay getMsgFromPaySystem(String txId);
}
