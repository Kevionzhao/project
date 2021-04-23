package com.zhao.bank1.service.impl;

import com.zhao.bank1.entity.AccountPay;
import com.zhao.bank1.model.AccountChangeEvent;
import com.zhao.bank1.service.PayService;
import com.zhao.bank1.mapper.AccountInfoMapper;
import com.zhao.bank1.spring.BankClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Admin
 */
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    AccountInfoMapper accountInfoMapper;
    @Autowired
    BankClient bankClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAccountBalance(AccountChangeEvent accountChangeEvent) {
        if (accountInfoMapper.isExistTx(accountChangeEvent.getTxNo())>0) {
            return;
        }
        accountInfoMapper.updateAccountBalance(accountChangeEvent.getAccountNo(), accountChangeEvent.getAmount()*-1);
        accountInfoMapper.addTx(accountChangeEvent.getTxNo());

    }

    @Override
    public AccountPay getMsgFromPaySystem(String txId) {
        AccountPay accountPay = bankClient.transfer(txId);
        if ("success".equals(accountPay.getResult())){
            AccountChangeEvent accountChangeEvent = new AccountChangeEvent();
            accountChangeEvent.setAccountNo(accountPay.getAccountNo());
            accountChangeEvent.setTxNo(accountPay.getId());
            accountChangeEvent.setAmount(accountPay.getPayAmount());
            updateAccountBalance(accountChangeEvent);
        }
        return accountPay;
    }
}
