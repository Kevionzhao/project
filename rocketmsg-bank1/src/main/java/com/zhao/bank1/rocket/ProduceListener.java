package com.zhao.bank1.rocket;

import com.alibaba.fastjson.JSONObject;
import com.zhao.bank1.mapper.AccountInfoMapper;
import com.zhao.bank1.model.AccountChangeEvent;
import com.zhao.bank1.service.Bank1Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Admin
 */
@Component
@Slf4j
@RocketMQTransactionListener(txProducerGroup = "producer_bank1_msg")
public class ProduceListener implements RocketMQLocalTransactionListener {
    
    @Autowired
    Bank1Service bank1Service;
    @Autowired
    AccountInfoMapper accountInfoMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try{
            String msg = new String((byte[]) message.getPayload());
            JSONObject jsonObject = JSONObject.parseObject(msg);
            String accountMsg1 = jsonObject.getString("accountEvent");
            AccountChangeEvent accountChangeEvent = JSONObject.parseObject(accountMsg1, AccountChangeEvent.class);
            bank1Service.updateAccountBalance(accountChangeEvent);
            return RocketMQLocalTransactionState.COMMIT;
        }catch (Exception e) {
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String msg = new String((byte[]) message.getPayload());
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String accountMsg1 = jsonObject.getString("accountEvent");
        AccountChangeEvent accountChangeEvent = JSONObject.parseObject(accountMsg1, AccountChangeEvent.class);
        if (accountInfoMapper.isExistTx(accountChangeEvent.getTxNo())>0){
            return RocketMQLocalTransactionState.COMMIT;
        }else {
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }
}
