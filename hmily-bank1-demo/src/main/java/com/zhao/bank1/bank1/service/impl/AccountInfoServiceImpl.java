package com.zhao.bank1.bank1.service.impl;

import com.zhao.bank1.bank1.mapper.AccountInfoMapper;
import com.zhao.bank1.bank1.spring.Bank2Client;
import com.zhao.bank1.bank1.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Admin
 */
@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    AccountInfoMapper accountInfoMapper;
    @Autowired
    Bank2Client bank2Client;
    /**
     * 本账户转账减钱
     *
     * @param accountNo
     * @param amount
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Hmily(confirmMethod = "commit",cancelMethod = "rollback")
    public void updateAccountBalance(String accountNo, Double amount) {
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        //幂等性
        if (accountInfoMapper.isExistTry(transId)>0){
            log.info("try method executed");
            return;
        }
        //悬挂处理
        if (accountInfoMapper.isExistConfirm(transId)>0||accountInfoMapper.isExistCancel(transId)>0){
            log.info("confirm 或 cancel已经执行txid:{}",transId);
            return;
        }
        if (accountInfoMapper.subtractAccountBalance(accountNo, amount)<=0){
            throw new HmilyRuntimeException("扣减失败");
        }
        //扣减成功追加try log
        accountInfoMapper.addTry(transId);
        //远程调用bank2处理交易
        if (!bank2Client.transfer(amount)){
            System.out.println("yuan cheng diao yong shi bai");
            throw new RuntimeException("远程调用失败");
        }
    }

    /**
     * confirm method
     * @param accountNo
     * @param amount
     */
    public void commit(String accountNo, Double amount){
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        System.out.println(transId);
    }

    /**
     * cancel method
     * @param accountNo
     * @param amount
     */
    @Transactional(rollbackFor = Exception.class)
    public void rollback(String accountNo, Double amount) {
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        if (accountInfoMapper.isExistCancel(transId)>0){
            log.info("cancel 已经执行一次，请勿在执行");
            return;
        }
        if (accountInfoMapper.isExistTry(transId)<=0){
            log.info("kong hui gun ");
            return;
        }
        accountInfoMapper.addAccountBalance(accountNo, amount);
        accountInfoMapper.addCancel(transId);
    }
}
