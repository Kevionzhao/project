package com.zhao.bank1.service.impl;

import com.zhao.bank1.entity.AccountPay;
import com.zhao.bank1.mapper.AccountPayMapper;
import com.zhao.bank1.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Admin
 */
@Service
@Slf4j
public class PayServerImpl implements PayService {

    @Autowired
    AccountPayMapper accountPayMapper;
    @Resource
    RocketMQTemplate rocketMQTemplate;
    @Override
    public AccountPay insertAccountPay(AccountPay accountPay) {
        int success = accountPayMapper.insertAccountPay(accountPay.getId(), accountPay.getAccountNo(), accountPay.getPayAmount(), "success");
        if (success>0) {
            AccountPay accountPay1 = accountPayMapper.findByIdTxNo(accountPay.getId());
            rocketMQTemplate.convertAndSend("topic_pay_notify",accountPay1);
            return accountPay1;
        }else {
            return null;
        }
    }

    @Override
    public AccountPay getAccountPay(String txId) {
        return accountPayMapper.findByIdTxNo(txId);
    }
}
