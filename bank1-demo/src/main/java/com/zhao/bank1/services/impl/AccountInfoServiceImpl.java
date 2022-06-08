package com.zhao.bank1.services.impl;

import com.zhao.bank1.bank1mapper.AccountInfoMapper;
import com.zhao.bank1.services.AccountInfoService;
import com.zhao.bank1.spring.Bank2Client;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    AccountInfoMapper accountInfoMapper;

    @Autowired
    Bank2Client bank2Client;


    /**
     * 账户更新
     * @param accountNo
     * @param amount
     */
    @Transactional(rollbackFor=Exception.class)
    @GlobalTransactional
    @Override
    public void updateAccountBalance(String accountNo, Double amount) {
        log.info("bank1 service begin,XID：{}", RootContext.getXID());
        //获取转账前的账户总金额
        int accountBalance = accountInfoMapper.getAccountBalance(accountNo);
        //扣减张三的金额
        accountInfoMapper.updateAccountBalance(accountNo,accountBalance-amount);
//        accountInfoMapper.insertAccountPassword();
        //调用李四微服务，转账
        String transfer = bank2Client.transfer(amount);
        if("fallback".equals(transfer)){
            //调用李四微服务异常
            throw new RuntimeException("调用李四微服务异常");
        }
        if(amount == 2){
            //人为制造异常
            throw new RuntimeException("bank1 make exception..");
        }
    }
}
