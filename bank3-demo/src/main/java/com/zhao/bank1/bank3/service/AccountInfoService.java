package com.zhao.bank1.bank3.service;

/**
 * Created by Administrator.
 * @author Admin
 */
public interface AccountInfoService {

    /**
     * 增加金额
     * @param accountNo
     * @param amount
     */
    public void updateAccountBalance(String accountNo, Double amount);
}
