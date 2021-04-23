package com.zhao.bank1.listener;

import com.alibaba.fastjson.JSON;
import com.zhao.bank1.entity.AccountPay;
import com.zhao.bank1.model.AccountChangeEvent;
import com.zhao.bank1.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Admin
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "topic_pay_notify",consumerGroup = "consume_group_notify")
public class NotifyListener implements RocketMQListener<AccountPay> {

    @Autowired
    PayService payService;
    @Override
    public void onMessage(AccountPay accountPay) {
        log.info("接收到消息：{}", JSON.toJSONString(accountPay));
        if ("success".equals(accountPay.getResult())) {
            AccountChangeEvent accountChangeEvent = new AccountChangeEvent();
            accountChangeEvent.setAccountNo(accountPay.getAccountNo());
            accountChangeEvent.setTxNo(accountPay.getId());
            accountChangeEvent.setAmount(accountPay.getPayAmount());
            payService.updateAccountBalance(accountChangeEvent);
        }
        log.info("处理消息完成：{}",JSON.toJSONString(accountPay));
    }
}
