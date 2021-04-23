package com.zhao.bank1.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisServiceImplTest {
    @Autowired
    RedisServiceImpl redisServiceImpl;

    @Test
    public void randomMember() {
        List<Object> list = redisServiceImpl.randomMember("set01", 0);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}