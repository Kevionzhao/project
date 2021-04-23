package com.zhao.bank1.service.impl;

import com.zhao.bank1.mapper.AccountInfoMapper;
import com.zhao.bank1.model.AccountChangeEvent;
import com.zhao.bank1.service.Bank2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Admin
 */
@Service
@Slf4j
public class Bank2ServiceImpl implements Bank2Service {

    @Autowired
    AccountInfoMapper accountInfoMapper;
    @Override
    public void addAccountBalance(AccountChangeEvent accountChangeEvent) {
        log.info("bank2 update local balance");
        if (accountInfoMapper.isExistTx(accountChangeEvent.getTxNo())>0){
            return;
        }
        accountInfoMapper.updateAccountBalance(accountChangeEvent.getAccountNo(),accountChangeEvent.getAmount());
        accountInfoMapper.addTx(accountChangeEvent.getTxNo());
    }
}
