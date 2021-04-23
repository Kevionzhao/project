package com.zhao.bank1.rocket;

import com.alibaba.fastjson.JSONObject;
import com.zhao.bank1.service.Bank2Service;
import com.zhao.bank1.model.AccountChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Admin
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "bank2_group_consume",topic = "transfer")
public class ProduceListenerBank2 implements RocketMQListener<String> {

    @Autowired
    Bank2Service bank2Service;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(String s) {
        String accountMsg1 = JSONObject.parseObject(s).getString("accountEvent");
        AccountChangeEvent accountChangeEvent = JSONObject.parseObject(accountMsg1, AccountChangeEvent.class);
        accountChangeEvent.setAccountNo("2");
        bank2Service.addAccountBalance(accountChangeEvent);
    }
}
