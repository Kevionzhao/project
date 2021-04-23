package com.zhao.bank1.bank1.service;

/**
 * @author Admin
 */
public interface AccountInfoService {
    /**
     * 本账户转账减钱
     * @param accountNo
     * @param amount
     */
    public void updateAccountBalance(String accountNo, Double amount);
}
