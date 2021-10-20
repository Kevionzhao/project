package com.zhao.bank1.controller;

import com.zhao.bank1.services.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
public class Bank1Controller {

    @Autowired
    AccountInfoService accountInfoService;

    @Resource
    DiscoveryClient discoveryClient;

    //张三转账
    @GetMapping("/transfer")
    public String transfer(Double amount){
        accountInfoService.updateAccountBalance("1",amount);
        return "bank1"+amount;
    }

    @GetMapping("/getServiceUrls")
    public List<String> getServerList(){
        List<String> list = discoveryClient.getServices();
        for (String url:list){
            System.out.println(url);
        }
        return list;
    }
}
