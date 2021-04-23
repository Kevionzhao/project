package com.zhao.bank1.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhao.bank1.mapper.AccountInfoMapper;
import com.zhao.bank1.model.AccountChangeEvent;
import com.zhao.bank1.service.Bank1Service;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Admin
 */
@Service
public class Bank1ServiceImpl implements Bank1Service {

    @Autowired
    AccountInfoMapper accountInfoMapper;
    @Autowired
    RocketMQTemplate rocketMQTemplate;
    /**
     * send update msg to other
     * @param accountChangeEvent
     */
    @Override
    public void sendAccountUpdateMessage(AccountChangeEvent accountChangeEvent) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountEvent",accountChangeEvent);
        String jsonString = jsonObject.toJSONString();
        Message<String> message = MessageBuilder.withPayload(jsonString).build();
        rocketMQTemplate.sendMessageInTransaction("producer_bank1_msg","transfer",message,"");
    }

    /**
     * update local balance
     *
     * @param accountChangeEvent
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAccountBalance(AccountChangeEvent accountChangeEvent) {
        if (accountInfoMapper.isExistTx(accountChangeEvent.getTxNo())>0){
            return;
        }
        accountInfoMapper.updateAccountBalance(accountChangeEvent.getAccountNo(),accountChangeEvent.getAmount()*-1);
        accountInfoMapper.addTx(accountChangeEvent.getTxNo());

    }
}
