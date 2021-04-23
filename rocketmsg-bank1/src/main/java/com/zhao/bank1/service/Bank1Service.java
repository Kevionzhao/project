package com.zhao.bank1.service;

import com.zhao.bank1.model.AccountChangeEvent;

/**
 * @author Admin
 */
public interface Bank1Service {

    /**
     * send update msg to other
     * @param accountChangeEvent
     */
    public  void sendAccountUpdateMessage(AccountChangeEvent accountChangeEvent);

    /**
     * update local balance
     * @param accountChangeEvent
     */
    public void updateAccountBalance(AccountChangeEvent accountChangeEvent);
}
