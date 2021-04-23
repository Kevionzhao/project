package com.zhao.bank1.bank2.service.impl;


import com.zhao.bank1.bank2.mapper.AccountInfoDao;
import com.zhao.bank1.bank2.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
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
    AccountInfoDao accountInfoDao;
    /**
     * 本账户转账减钱
     *
     * @param accountNo
     * @param amount
     */
    @Override
    @Hmily(confirmMethod = "commit",cancelMethod = "rollback")
    public void updateAccountBalance(String accountNo, Double amount) {
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("try method record txid:{}",transId);

    }

    /**
     * confirm method
     * @param accountNo
     * @param amount
     */
    @Transactional(rollbackFor = Exception.class)
    public void commit(String accountNo, Double amount){
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        System.out.println("========================"+transId);
        if (accountInfoDao.isExistConfirm(transId)>0){
            log.info("++++++++++++++已经执行不需在执行");
            return;
        }
        accountInfoDao.addAccountBalance(accountNo,amount);
        accountInfoDao.addConfirm(transId);
    }

    /**
     * cancel method
     * @param accountNo
     * @param amount
     */
    public void rollback(String accountNo, Double amount) {
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
       log.info("cancel method txid:{}",transId);
    }
}
